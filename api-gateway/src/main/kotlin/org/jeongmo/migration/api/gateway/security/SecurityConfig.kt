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

    private val allowUrl = arrayOf(
        "/eureka/**",
    )

    private val endpointRole = "ENDPOINT_ADMIN"

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .authorizeExchange {
                it
                    .pathMatchers(*allowUrl).permitAll()
                    .pathMatchers("/actuator/**").hasRole(endpointRole)
                    .anyExchange().permitAll()
            }
            .formLogin {it.disable()}
            .csrf {it.disable()}
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailService(): ReactiveUserDetailsService {
        val user = User.builder()
            .username(this.username)
            .password(passwordEncoder().encode(this.password))
            .roles(endpointRole)
            .build()
        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}