package org.jeongmo.migration.api.gateway.security

import org.jeongmo.migration.api.gateway.security.filter.HeaderTokenAuthenticationFilter
import org.jeongmo.migration.api.gateway.security.handler.CustomAccessDeniedHandler
import org.jeongmo.migration.api.gateway.security.handler.CustomServerAuthenticationEntryPoint
import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.web.server.WebFilter

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${actuator.username}") private val username: String,
    @Value("\${actuator.password}") private val password: String,
    private val tokenUtil: TokenUtil,
    private val httpResponseUtil: HttpResponseUtil,
) {

    private val allowUrl = arrayOf(
        "/auth/sign-up",
        "/auth/login",
        "/auth/reissue",
        "/health-check/**",
    )

    @Bean
    @Order(0)
    fun actuatorFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .securityMatcher(PathPatternParserServerWebExchangeMatcher("/actuator/**"))
            .authorizeExchange {
                it
                    .anyExchange().authenticated()
            }
            .csrf{it.disable()}
            .formLogin{it.disable()}
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .authorizeExchange {
                it
                    .pathMatchers(*allowUrl).permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
            .exceptionHandling {
                it
                    .accessDeniedHandler(serverAccessDeniedHandler())
                    .authenticationEntryPoint(serverAuthenticationEntryPoint())
            }
            .formLogin {it.disable()}
            .csrf {it.disable()}
            .httpBasic {it.disable()}
        return http.build()
    }

    @Bean
    fun authenticationFilter(): WebFilter = HeaderTokenAuthenticationFilter(tokenUtil, httpResponseUtil)

    @Bean
    fun serverAccessDeniedHandler(): ServerAccessDeniedHandler = CustomAccessDeniedHandler(httpResponseUtil)

    @Bean
    fun serverAuthenticationEntryPoint(): ServerAuthenticationEntryPoint = CustomServerAuthenticationEntryPoint(httpResponseUtil)

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