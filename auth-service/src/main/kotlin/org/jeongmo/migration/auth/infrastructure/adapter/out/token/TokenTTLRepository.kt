package org.jeongmo.migration.auth.infrastructure.adapter.out.token

import org.jeongmo.migration.auth.application.constants.TokenType
import org.jeongmo.migration.auth.application.error.code.TokenErrorCode
import org.jeongmo.migration.auth.application.error.exception.TokenException
import org.jeongmo.migration.auth.application.port.out.token.TokenRepository
import org.jeongmo.migration.common.utils.ttl.TTLRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

@Repository
class TokenTTLRepository(
    @Value("\${token.jwt.expiration-time.access-token}") private val accessTokenExpiration: Long,
    @Value("\${token.jwt.expiration-time.refresh-token}") private val refreshTokenExpiration: Long,
    private val ttlRepository: TTLRepository,
): TokenRepository {

    private val refreshPrefix: String = "REFRESH_TOKEN"
    private val blackListPrefix: String = "BLACK_LIST"

    override fun saveRefreshToken(id: Long, token: String): Boolean = ttlRepository.save("${refreshPrefix}:$id", token, refreshTokenExpiration)

    override fun blackListToken(token: String): Boolean = ttlRepository.save("${blackListPrefix}:$token", true, accessTokenExpiration)

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