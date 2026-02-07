package org.jeongmo.migration.common.auth.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ID_NAME
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ROLE_NAME
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolderStrategy
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.filter.OncePerRequestFilter

class HttpServletAuthenticationFilter(
    private val securityContextRepository: SecurityContextRepository = RequestAttributeSecurityContextRepository(),
    private val securityContextHolderStrategy: SecurityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy(),
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val idHeader = request.getHeader(INTERNAL_SERVER_AUTH_ID_NAME)
        val roleHeader = request.getHeader(INTERNAL_SERVER_AUTH_ROLE_NAME)

        if (idHeader.isNullOrEmpty() || roleHeader.isNullOrEmpty()) {
            return filterChain.doFilter(request, response)
        }

        val userRole = roleHeader.let {
            it.split(", ").map{
                SimpleGrantedAuthority(it)
            }
        }

        val authentication = UsernamePasswordAuthenticationToken(idHeader.toLongOrNull() ?: idHeader, null, userRole)
        successfulAuthentication(request, response, authentication)
        return filterChain.doFilter(request, response)
    }

    private fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val context = securityContextHolderStrategy.createEmptyContext()
        context.authentication = authentication
        securityContextHolderStrategy.context = context
        securityContextRepository.saveContext(context, request, response)
    }
}