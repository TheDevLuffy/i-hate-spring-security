package dev.milzipmoza.secutiry.config

import dev.milzipmoza.secutiry.filter.UserAuthenticationFilter
import dev.milzipmoza.secutiry.handler.UserAuthenticationSuccessHandler
import dev.milzipmoza.secutiry.provider.UserAuthenticationProvider
import dev.milzipmoza.secutiry.security.JwtTokenAuthenticator
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf {
                    it.disable() // csrf 비활성화, 활성화 되면 Cross-Site Request Forgery 요청 난수를 포함시켜 그 난수가 없으면 동작을 거부하도록 한다.
                }
                .authorizeRequests {
                    it.anyRequest().permitAll()  // 인증받은 요청은 모두 허용
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 기반 인증이 아니기 때문에 비활성화 처리
                }
                .formLogin {
                    it.disable() // form 로그인 역시 비활성화 처리
                }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(userAuthenticationProvider())
    }

    /**
     * 비밀번호 암호화용, 소셜 로그인이 붙으면 사용하지 않을 예정
     */
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userAuthenticationFilter(): UserAuthenticationFilter =
            UserAuthenticationFilter(authenticationManager())
                .apply {
                    setFilterProcessesUrl("/user/login")
                    setAuthenticationSuccessHandler(userAuthenticationSuccessHandler())
                    afterPropertiesSet()
                }

    @Bean
    fun jwtTokenAuthenticator(): JwtTokenAuthenticator = JwtTokenAuthenticator()

    @Bean
    fun userAuthenticationSuccessHandler(): UserAuthenticationSuccessHandler = UserAuthenticationSuccessHandler()

    @Bean
    fun userAuthenticationProvider(): UserAuthenticationProvider = UserAuthenticationProvider()
}