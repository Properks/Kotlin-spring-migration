package org.jeongmo.practice.global.security.token.manager

import jakarta.servlet.http.HttpServletRequest

interface TokenManager {
    fun hasToken(request: HttpServletRequest): Boolean
    fun getToken(request: HttpServletRequest): String
}