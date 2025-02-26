package my.project.onlineAuctionBackend.securities

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/**").permitAll() // ✅ เปิดให้ Register/Login ใช้ได้
                it.requestMatchers("/api/users/**").permitAll()
                it.requestMatchers("/api/products/**").permitAll() // ✅ เปิดให้ทุกคนใช้ API นี้
                it.requestMatchers("/api/category/**").permitAll()
                it.anyRequest().authenticated() // ❌ API อื่นต้อง Login ก่อน
            }
        return http.build()
    }

}