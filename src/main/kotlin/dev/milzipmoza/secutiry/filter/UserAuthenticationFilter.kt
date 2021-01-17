package dev.milzipmoza.secutiry.filter

import com.fasterxml.jackson.databind.ObjectMapper
import dev.milzipmoza.secutiry.domain.dto.UserDto
import dev.milzipmoza.secutiry.util.logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    private val log = logger(this)
    private val objectMapper = ObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val authRequest = try {
            val user: UserDto = objectMapper.readValue(request.inputStream, UserDto::class.java)
            UsernamePasswordAuthenticationToken(user.ofEmail, user.ofPassword)
        } catch (e: Exception) {
            log.info("[UserAuthenticationFilter] error=$e")
            throw IllegalArgumentException("요청을 확인해주세요.")
        }
        setDetails(request, authRequest)
        return this.authenticationManager.authenticate(authRequest)
    }
}