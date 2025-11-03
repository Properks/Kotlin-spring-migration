package org.jeongmo.migration.auth.infrastructure.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun disableFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement {it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}

        return http.build()
    }
}