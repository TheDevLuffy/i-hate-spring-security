package dev.milzipmoza.secutiry.handler

import dev.milzipmoza.secutiry.security.JwtTokenAuthenticator
import dev.milzipmoza.secutiry.domain.dto.AuthenticatedUserDetails
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UserAuthenticationSuccessHandler(
        private val jwtTokenAuthenticator: JwtTokenAuthenticator
) : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val userDetails = authentication.principal as AuthenticatedUserDetails
        val user = userDetails.user
        val token = jwtTokenAuthenticator.generateJwtToken(user)
        response.addHeader("Authorization", "Bearer $token")
    }
}