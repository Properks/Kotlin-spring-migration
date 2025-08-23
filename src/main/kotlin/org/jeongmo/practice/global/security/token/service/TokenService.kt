package org.jeongmo.practice.global.security.token.service

interface TokenService<T> {
    fun createAccessToken(memberInfo: T): String
    fun createRefreshToken(memberInfo: T): String
    fun isValid(token: String): Boolean
    fun getSubject(token: String): String
}