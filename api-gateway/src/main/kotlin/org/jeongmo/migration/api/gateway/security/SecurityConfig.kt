package org.jeongmo.migration.api.gateway.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${actuator.username}") private val username: String,
    @Value("\${actuator.password}") private val password: String,
) {

    @Bean
    fun filterCharin(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .authorizeExchange {
                it
                    .pathMatchers("/actuator/**").authenticated()
                    .anyExchange().permitAll()
            }
            .csrf{it.disable()}
            .formLogin{it.disable()}
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailService(): ReactiveUserDetailsService {
        val user = User.builder()
            .username(this.username)
            .password(passwordEncoder().encode(this.password))
            .roles("ENDPOINT_ADMIN")
            .build()
        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}