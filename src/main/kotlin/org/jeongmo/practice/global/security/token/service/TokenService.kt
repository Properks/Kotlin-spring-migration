package org.jeongmo.practice.global.security.token.service

import org.springframework.security.core.userdetails.UserDetails

interface TokenService {
    fun createAccessToken(memberInfo: UserDetails): String
    fun createRefreshToken(memberInfo: UserDetails): String
    fun isValid(token: String): Boolean
    fun getSubject(token: String): String
}