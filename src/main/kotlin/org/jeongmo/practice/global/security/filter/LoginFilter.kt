package org.jeongmo.practice.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.practice.global.security.handler.FilterExceptionHandler
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.http.HttpMethod
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.jvm.Throws

abstract class LoginFilter(
    private val securityContextRepository: SecurityContextRepository,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val exceptionHandler: FilterExceptionHandler,
): OncePerRequestFilter() {

    private val requestMatcher: RequestMatcher = PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login")
    private val securityContextHolderStrategy: SecurityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            if (requireLoginLogic(request)) {
                val authentication: Authentication = attemptAuthentication(request)

                successAuthentication(request, response, authentication)
            }
            else {
                filterChain.doFilter(request, response)
            }
        } catch (e: ServerApplicationException) {
            exceptionHandler.handleServerApplicationException(request, response, exception = e)
        } catch (e: Exception) {
            exceptionHandler.handleException(request, response, exception = e)
        }

    }

    @Throws(ServerApplicationException::class, AuthenticationException::class)
    abstract fun attemptAuthentication(request: HttpServletRequest): Authentication

    private fun successAuthentication(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): Unit {
        val context: SecurityContext = securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        securityContextRepository.saveContext(context, request, response)
        securityContextHolderStrategy.context = context
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication)
    }

    private fun requireLoginLogic(request: HttpServletRequest): Boolean {
        return requestMatcher.matches(request)
    }
}