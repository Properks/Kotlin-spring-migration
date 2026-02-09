package org.jeongmo.migration.bought.item.infrastructure.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.Filter
import org.jeongmo.migration.common.auth.filter.HttpServletAuthenticationFilter
import org.jeongmo.migration.common.auth.handler.AuthenticationHandler
import org.jeongmo.migration.common.auth.handler.AuthorizationHandler
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper,
) {

    private val allowUrl = arrayOf(
        "/eureka/**",
        "/internal/api/bought-items/**"
    )

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(*allowUrl).permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/bought-items/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

            }
            .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .csrf {it.disable()}
            .httpBasic {it.disable()}
            .formLogin {it.disable()}
            .sessionManagement {it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .exceptionHandling {
                it
                    .accessDeniedHandler(authorizationHandler())
                    .authenticationEntryPoint(authenticationHandler())
            }
        return http.build()
    }

    @Bean
    fun authenticationFilter(): Filter = HttpServletAuthenticationFilter()

    @Bean
    fun authenticationHandler(): AuthenticationEntryPoint = AuthenticationHandler(failureResponseWriter, objectMapper)

    @Bean
    fun authorizationHandler(): AccessDeniedHandler = AuthorizationHandler(failureResponseWriter, objectMapper)
}