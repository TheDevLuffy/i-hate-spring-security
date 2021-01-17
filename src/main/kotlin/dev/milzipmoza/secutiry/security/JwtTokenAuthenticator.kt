package dev.milzipmoza.secutiry.security

import dev.milzipmoza.secutiry.domain.User
import dev.milzipmoza.secutiry.domain.UserRole
import dev.milzipmoza.secutiry.util.logger
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

class JwtTokenAuthenticator {
    @Value("jwt.secret-key")
    private lateinit var secretKey: String

    private val log = logger(this)

    fun generateJwtToken(user: User): String =
            Jwts.builder().apply {
                setSubject(user.email)
                setHeader(createHeader())
                setClaims(createClaims(user))
                setExpiration(createExpireDate())
                signWith(SignatureAlgorithm.HS256, createSigningKey())
            }.compact()

    fun isValidToken(token: String): Boolean =
            try {
                val claims = getClaims(token)
                true
            } catch (e: Exception) {
                when (e) {
                    is ExpiredJwtException -> {
                        log.error("Jwt Expired")
                    }
                    is JwtException -> {
                        log.error("Jwt Invalid")
                    }
                    is NullPointerException -> {
                        log.error("Jwt Not Found")
                    }
                    else -> {
                    }
                }
                false
            }

    private fun createHeader(): Map<String, Any> =
            mapOf(
                    "typ" to "JWT",
                    "alg" to "HS256",
                    "regDate" to System.currentTimeMillis()
            )


    private fun createClaims(user: User): Map<String, Any> =
            mapOf(
                    CLAIMS_EMAIL to user.email,
                    CLAIMS_NAME to user.name,
                    CLAIMS_ROLE to user.role
            )

    private fun createExpireDate(): Date =
            Calendar.getInstance().apply {
                add(Calendar.DATE, 30)
            }.time

    private fun createSigningKey(): Key =
            SecretKeySpec(DatatypeConverter.parseBase64Binary(secretKey), SignatureAlgorithm.HS256.jcaName)

    private fun getClaims(token: String): Claims =
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).body

    private fun getName(token: String): String =
            getClaims(token)[CLAIMS_NAME] as String

    private fun getEmail(token: String): String =
            getClaims(token)[CLAIMS_EMAIL] as String

    private fun getRole(token: String): UserRole =
            getClaims(token)[CLAIMS_ROLE] as UserRole

    companion object {
        private const val CLAIMS_NAME = "name"
        private const val CLAIMS_EMAIL = "email"
        private const val CLAIMS_ROLE = "role"
    }
}