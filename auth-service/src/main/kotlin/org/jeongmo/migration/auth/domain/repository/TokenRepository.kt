package org.jeongmo.migration.auth.domain.repository

interface TokenRepository {

    fun saveToken(token: String, ttl: Long): Boolean
    fun removeToken(token: String): Boolean
    fun blacklistToken(token: String, ttl: Long): Boolean

}