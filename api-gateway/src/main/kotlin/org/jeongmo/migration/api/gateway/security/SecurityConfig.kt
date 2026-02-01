package org.jeongmo.migration.api.gateway.security

import org.jeongmo.migration.api.gateway.security.filter.HeaderTokenAuthenticationFilter
import org.jeongmo.migration.api.gateway.security.filter.LogoutFilter
import org.jeongmo.migration.api.gateway.security.handler.CustomAccessDeniedHandler
import org.jeongmo.migration.api.gateway.security.handler.CustomServerAuthenticationEntryPoint
import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.jeongmo.migration.common.token.domain.repository.ReactiveTokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.web.server.WebFilter

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${actuator.username}") private val username: String,
    @Value("\${actuator.password}") private val password: String,
    private val tokenUtil: TokenUtil,
    private val httpResponseUtil: HttpResponseUtil,
    private val reactiveTokenRepository: ReactiveTokenRepository,
) {

    private val logoutUrl = "/api/v1/auth/logout"
    private val allowUrl = arrayOf(
        "/auth/sign-up",
        "/auth/login",
        "/auth/reissue",
        "/actuator/health",
    )

    private val merchantPatterns = arrayOf(
        asPattern(HttpMethod.POST, "/items"),
        asPattern(HttpMethod.PATCH, "/items/**"),
        asPattern(HttpMethod.DELETE, "/items/**"),
    )

    private val adminPatterns = arrayOf(
        asPattern(HttpMethod.PATCH, "/bought-items/**")
    )

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        http
            .authorizeExchange {
                it
                    .matchers(*merchantPatterns).hasAnyRole("MERCHANT", "ADMIN")
                    .matchers(*adminPatterns).hasRole("ADMIN")
                    .pathMatchers(*allowUrl).permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
            .addFilterAt(logoutFilter(), SecurityWebFiltersOrder.LOGOUT)
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
    fun authenticationFilter(): WebFilter = HeaderTokenAuthenticationFilter(tokenUtil, httpResponseUtil, reactiveTokenRepository)

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

    @Bean
    fun logoutFilter(): WebFilter = LogoutFilter(logoutUrl, tokenUtil, reactiveTokenRepository, httpResponseUtil)

    private fun asPattern(method: HttpMethod, pattern: String): ServerWebExchangeMatcher {
        return PathPatternParserServerWebExchangeMatcher(pattern, method)
    }
}