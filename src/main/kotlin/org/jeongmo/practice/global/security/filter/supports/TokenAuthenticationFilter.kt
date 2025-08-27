package org.jeongmo.practice.global.security.filter.supports

import jakarta.servlet.http.HttpServletRequest
import org.jeongmo.practice.global.error.code.TokenErrorCode
import org.jeongmo.practice.global.error.exception.TokenException
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.jeongmo.practice.global.security.filter.AuthenticationFilter
import org.jeongmo.practice.global.security.handler.FilterExceptionHandler
import org.jeongmo.practice.global.security.token.manager.TokenManager
import org.jeongmo.practice.global.security.token.service.TokenService
import org.jeongmo.practice.global.security.token.service.TokenStorageService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.context.SecurityContextRepository

class TokenAuthenticationFilter(
    private val tokenManager: TokenManager,
    private val tokenService: TokenService<CustomUserDetails>,
    private val tokenStorageService: TokenStorageService,
    private val userDetailsService: UserDetailsService,

    exceptionHandler: FilterExceptionHandler,
    securityContextRepository: SecurityContextRepository,
): AuthenticationFilter(securityContextRepository, exceptionHandler) {

    override fun getAuthentication(request: HttpServletRequest): Authentication {
        val token: String = tokenManager.getToken(request)
        if (tokenService.isValid(token) && !tokenStorageService.isBlackList(token)) {
            val subject = tokenService.getSubject(token)
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(subject)
            return UsernamePasswordAuthenticationToken.authenticated(userDetails.username, userDetails.password, userDetails.authorities)
        }
        else {
            throw TokenException(TokenErrorCode.INVALID_TOKEN)
        }
    }

    override fun requireAuthentication(request: HttpServletRequest): Boolean = tokenManager.hasToken(request)
}