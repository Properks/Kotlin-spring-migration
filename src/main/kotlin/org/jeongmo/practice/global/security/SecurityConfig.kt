package org.jeongmo.practice.global.security

import jakarta.servlet.Filter
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.jeongmo.practice.global.security.filter.supports.JsonLoginFilter
import org.jeongmo.practice.global.security.filter.supports.TokenAuthenticationFilter
import org.jeongmo.practice.global.security.handler.JsonResponseFilterExceptionHandler
import org.jeongmo.practice.global.security.token.manager.AuthorizationHeaderTokenManager
import org.jeongmo.practice.global.security.token.service.TokenService
import org.jeongmo.practice.global.util.HttpResponseWriter
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository

@Configuration
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val tokenService: TokenService<CustomUserDetails>,
    private val userDetailsService: UserDetailsService,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val httpResponseWriter: HttpResponseWriter<DefaultResponseSuccessReasonDTO, DefaultResponseErrorReasonDTO>,
) {

    private val allowUrl : Array<String> = arrayOf(
        "/login",
        "/sign-up",
        "/reissue"
    );

    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it
                    .requestMatchers(*allowUrl).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic {it.disable()}
            .csrf { it.disable() }
            .formLogin {it.disable()}
        return http.build();
    }

    @Bean
    fun loginFilter(): Filter = JsonLoginFilter(
        authenticationManager = authenticationManager(),
        securityContextRepository = securityContextRepository(),
        authenticationSuccessHandler = authenticationSuccessHandler,
        filterExceptionHandler = filterExceptionHandler(),
    )
    @Bean
    fun authenticationFilter(): Filter = TokenAuthenticationFilter(
        tokenManager = tokenManager(),
        exceptionHandler = filterExceptionHandler(),
        userDetailsService = userDetailsService,
        tokenService = tokenService,
        securityContextRepository = securityContextRepository(),
    )

    @Bean
    fun tokenManager() = AuthorizationHeaderTokenManager()

    @Bean
    fun authenticationManager(): AuthenticationManager = authenticationConfiguration.authenticationManager

    @Bean
    fun securityContextRepository(): SecurityContextRepository = RequestAttributeSecurityContextRepository()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterExceptionHandler() = JsonResponseFilterExceptionHandler(httpResponseWriter)
}