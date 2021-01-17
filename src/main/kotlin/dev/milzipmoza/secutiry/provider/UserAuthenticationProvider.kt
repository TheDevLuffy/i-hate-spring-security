package dev.milzipmoza.secutiry.provider

import dev.milzipmoza.secutiry.domain.User
import dev.milzipmoza.secutiry.domain.UserRepository
import dev.milzipmoza.secutiry.domain.dto.AuthenticatedUserDetails
import dev.milzipmoza.secutiry.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserAuthenticationProvider: AuthenticationProvider {

    private val log = logger(this)

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication as UsernamePasswordAuthenticationToken
        val email = token.name
        val password = token.credentials as String
        val user: User = userRepository.findByEmail(email)
                ?: throw IllegalArgumentException("찾을 수 없습니다.")
        val userDetails = AuthenticatedUserDetails(user, setOf(SimpleGrantedAuthority(user.role.name)))
        if (passwordEncoder.matches(password, userDetails.password)) {
            return UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
        }

        log.warn("Invalid password")
        throw BadCredentialsException("Invalid password")
    }

    override fun supports(authentication: Class<*>): Boolean =
            authentication == UsernamePasswordAuthenticationToken::class.java
}