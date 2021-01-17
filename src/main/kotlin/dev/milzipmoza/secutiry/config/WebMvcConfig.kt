package dev.milzipmoza.secutiry.config

import dev.milzipmoza.secutiry.interceptor.JwtTokenInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    /**
     * 정적 자원 제공 클래스
     * 사용되지 않을 예정, api 만 제공하는 서버이기 때문
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**").addResourceLocations(*CLASSPATH_RESOURCE_LOCATIONS)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtTokenInterceptor()).addPathPatterns("/user/findAll")
    }

    @Bean
    fun jwtTokenInterceptor(): JwtTokenInterceptor = JwtTokenInterceptor()

    companion object {
        private val CLASSPATH_RESOURCE_LOCATIONS = arrayOf("classpath:/static/", "classpath:/public/", "classpath:/",
                "classpath:/resources/", "classpath:/META-INF/resources/", "classpath:/META-INF/resources/webjars/")
    }
}
