package dev.milzipmoza.secutiry.handler

import dev.milzipmoza.secutiry.domain.dto.AuthenticatedUserDetails
import dev.milzipmoza.secutiry.security.JwtTokenAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthenticationSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    @Autowired
    private lateinit var jwtTokenAuthenticator: JwtTokenAuthenticator

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val userDetails = authentication.principal as AuthenticatedUserDetails
        val user = userDetails.user
        val token = jwtTokenAuthenticator.generateJwtToken(user)
        response.addHeader("Authorization", "Bearer $token")
    }
}