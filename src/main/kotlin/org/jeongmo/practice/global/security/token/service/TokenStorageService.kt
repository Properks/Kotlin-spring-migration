package org.jeongmo.practice.global.security.token.service

interface TokenStorageService {

    fun saveRefreshToken(username: String, token: String)
    fun getRefreshToken(username: String): String?
    fun saveBlackList(token: String)
    fun isBlackList(token: String): Boolean
    fun deleteBlackList(token: String)
}