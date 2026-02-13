package org.jeongmo.migration.member.infrastructure.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.Filter
import org.jeongmo.migration.common.auth.filter.HttpServletAuthenticationFilter
import org.jeongmo.migration.common.auth.filter.InternalServerAuthenticationFilter
import org.jeongmo.migration.common.auth.handler.AuthenticationHandler
import org.jeongmo.migration.common.auth.handler.AuthorizationHandler
import org.jeongmo.migration.common.enums.member.Role
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    @Value("\${internal-service.authentication.token}") private val token: String,
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper,
) {

    private val allowUrl = arrayOf(
        "/eureka/**",
    )

    private val internalServiceUrl = arrayOf(
        "/internal/api/members",
        "/internal/api/members/verify",
    )

    @Bean
    fun disableFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(*allowUrl).permitAll()
                    .requestMatchers(*internalServiceUrl).hasRole(Role.INTERNAL_SERVICE.name)
                    .anyRequest().authenticated()
            }
            .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAt(internalServiceAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement {it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .exceptionHandling {
                it
                    .accessDeniedHandler(authorizationHandler())
                    .authenticationEntryPoint(authenticationHandler())
            }

        return http.build()
    }

    @Bean
    fun internalServiceAuthenticationFilter(): Filter = InternalServerAuthenticationFilter(token)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationFilter(): Filter = HttpServletAuthenticationFilter()

    @Bean
    fun authenticationHandler(): AuthenticationEntryPoint = AuthenticationHandler(failureResponseWriter, objectMapper)

    @Bean
    fun authorizationHandler(): AccessDeniedHandler = AuthorizationHandler(failureResponseWriter, objectMapper)
}