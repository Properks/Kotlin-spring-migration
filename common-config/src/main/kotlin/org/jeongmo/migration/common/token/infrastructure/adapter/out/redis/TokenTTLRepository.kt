package org.jeongmo.migration.common.token.infrastructure.adapter.out.redis

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.domain.repository.TokenRepository
import org.jeongmo.migration.common.utils.ttl.TTLRepository

class TokenTTLRepository(
    private val ttlRepository: TTLRepository,
    private val accessTokenExpiration: Long,
): TokenRepository {

    private val refreshPrefix: String = "REFRESH_TOKEN"
    private val blackListPrefix: String = "BLACK_LIST"

    override fun saveToken(id: Long, token: String, type: TokenType): Boolean {
        return when (type) {
            TokenType.BLACK_LIST -> {
                ttlRepository.save("${blackListPrefix}:$token", true, accessTokenExpiration)
            }
            TokenType.REFRESH -> {
                ttlRepository.save("${refreshPrefix}:$id", token, accessTokenExpiration)
            }
            else -> {
                throw TokenException(TokenErrorCode.UNSUPPORTED_TYPE)
            }
        }
    }

    override fun getToken(id: Long, type: TokenType): String? {
        return when(type) {
            TokenType.REFRESH-> {
                ttlRepository.findByKey(key = "${refreshPrefix}:$id", String::class.java)
            }
            else -> {
                throw TokenException(TokenErrorCode.UNSUPPORTED_TYPE)
            }
        }
    }
    
    override fun isBlackList(token: String): Boolean {
        return ttlRepository.has("${blackListPrefix}:$token")
    }

    override fun removeToken(id: Long?, token: String?, type: TokenType): Boolean {
        return if (type == TokenType.BLACK_LIST && token != null) {
            ttlRepository.deleteValue("$blackListPrefix:$token")
        }
        else if (type == TokenType.REFRESH && id != null) {
            ttlRepository.deleteValue("$refreshPrefix:$id")
        }
        else {
            false
        }
    }

}