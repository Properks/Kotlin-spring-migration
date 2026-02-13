package org.jeongmo.migration.common.auth.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_TOKEN_NAME
import org.jeongmo.migration.common.auth.domain.InternalServiceAuthenticationToken
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter

class InternalServerAuthenticationFilter(
     private val authenticationToken: String?,
    private val securityContextRepository: SecurityContextRepository = RequestAttributeSecurityContextRepository(),
    private val securityContextHolderStrategy: SecurityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy(),
): OncePerRequestFilter() {

    private val internalApiPrefix = "/internal/api"
    private val log = LoggerFactory.getLogger(InternalServerAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if (request.requestURI.startsWith(internalApiPrefix) && !isAuthenticated(request)) {

            log.info("$authenticationToken")
            authenticationToken?.let {
                val header = request.getHeader(INTERNAL_SERVER_TOKEN_NAME)
                log.info("$header")
                if (header != null && it == header) {
                    successfulAuthentication(request, response)
                }
            }
            log.info("${SecurityContextHolder.getContext().authentication}")
            filterChain.doFilter(request, response)
        }
        else {
            filterChain.doFilter(request, response)
        }
    }

    private fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse) {
        val authentication = InternalServiceAuthenticationToken()
        val context = securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        securityContextHolderStrategy.context = context
        securityContextRepository.saveContext(context, request, response)
    }

    private fun isAuthenticated(request: HttpServletRequest): Boolean =
        securityContextRepository.loadDeferredContext(request).get()?.authentication?.isAuthenticated ?: false || securityContextHolderStrategy.context?.authentication?.isAuthenticated
                ?: false
}