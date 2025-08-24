package org.jeongmo.practice.global.security.token.manager

import jakarta.servlet.http.HttpServletRequest

class AuthorizationHeaderTokenManager: TokenManager {

    private val authorizationHeader: String = "Authorization"
    private val tokenPrefix: String = "Bearer "

    override fun hasToken(request: HttpServletRequest): Boolean = request.getHeader(authorizationHeader)?.startsWith(tokenPrefix) == true

    override fun getToken(request: HttpServletRequest): String = request.getHeader(authorizationHeader).substring(tokenPrefix.length)
}