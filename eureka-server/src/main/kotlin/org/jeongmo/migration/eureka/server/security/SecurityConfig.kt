package org.jeongmo.migration.eureka.server.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${eureka.security.username}") private val username: String,
    @Value("\${eureka.security.password}") private val password: String,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {it.disable()}
            .authorizeHttpRequests { it
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.builder()
            .username(this.username)
            .password(passwordEncoder().encode(this.password))
            .build()
        return InMemoryUserDetailsManager(userDetails)
    }
}