package org.jeongmo.practice.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.practice.global.security.handler.FilterExceptionHandler
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.jvm.Throws

abstract class AuthenticationFilter(
    private val securityContextRepository: SecurityContextRepository,
    private val exceptionHandler: FilterExceptionHandler,
): OncePerRequestFilter() {

    private val securityContextHolderStrategy: SecurityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (requireAuthentication(request)) {
            try {
                val authentication: Authentication = getAuthentication(request)

                onAuthentication(request, response, authentication)
            } catch (e: ServerApplicationException) {
                exceptionHandler.handleServerApplicationException(request, response, exception = e)
            } catch (e: Exception) {
                exceptionHandler.handleException(request, response, exception = e)
            }
        }
        filterChain.doFilter(request, response)
    }

    @Throws(ServerApplicationException::class, AuthenticationException::class)
    protected abstract fun requireAuthentication(request: HttpServletRequest): Boolean
    protected abstract fun getAuthentication(request: HttpServletRequest): Authentication

    protected open fun onAuthentication(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val context: SecurityContext = securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        securityContextRepository.saveContext(context, request, response)
        securityContextHolderStrategy.context = context
    }

}