package org.jeongmo.practice.global.security.token.util

import org.springframework.security.core.userdetails.UserDetails

interface TokenUtil<T> {
    fun createToken(memberInfo: UserDetails, expirationMillis: Long): String
    fun getClaims(token: String) : T
}