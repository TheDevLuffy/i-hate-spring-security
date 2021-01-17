package dev.milzipmoza.secutiry.interceptor

import dev.milzipmoza.secutiry.security.JwtTokenAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenInterceptor : HandlerInterceptor {

    @Autowired
    private lateinit var jwtTokenAuthenticator: JwtTokenAuthenticator

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val header: String? = request.getHeader("Authorization")

        if (header != null) {
            val token = header.split(" ")[1]
            if (jwtTokenAuthenticator.isValidToken(token)) {
                return true
            }
        }

        response.sendRedirect("/error/unauthorized")
        return false
    }
}