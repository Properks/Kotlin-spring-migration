package org.jeongmo.practice.global.security.token.util

interface TokenUtil<T, U> {
    fun createToken(memberInfo: T, expirationMillis: Long): String
    fun getClaims(token: String) : U
}