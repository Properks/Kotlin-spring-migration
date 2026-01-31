package org.jeongmo.migration.common.token.infrastructure.adapter.out.redis

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.domain.repository.ReactiveTokenRepository
import org.jeongmo.migration.common.utils.ttl.ReactiveTTLRepository
import reactor.core.publisher.Mono

class TokenReactiveTTLRepository(
    private val reactiveTTLRepository: ReactiveTTLRepository,
    private val tokenExpiration: Long,
): ReactiveTokenRepository{

    private val refreshPrefix: String = "REFRESH_TOKEN"
    private val blackListPrefix: String = "BLACK_LIST"

    override fun saveToken(id: Long, token: String, type: TokenType): Mono<Boolean> {
        return when (type) {
            TokenType.BLACK_LIST -> {
                reactiveTTLRepository.save("${blackListPrefix}:$token", true, tokenExpiration)
            }
            TokenType.REFRESH -> {
                reactiveTTLRepository.save("${refreshPrefix}:$id", token, tokenExpiration)
            }
            else -> {
                Mono.error(TokenException(TokenErrorCode.UNSUPPORTED_TYPE))
            }
        }
    }

    override fun getToken(id: Long, type: TokenType): Mono<String?> {
        return when(type) {
            TokenType.REFRESH-> {
                reactiveTTLRepository.findByKey(key = "${refreshPrefix}:$id", String::class.java)
            }
            else -> {
                throw TokenException(TokenErrorCode.UNSUPPORTED_TYPE)
            }
        }
    }

    override fun isBlackList(token: String): Mono<Boolean> {
        return reactiveTTLRepository.has("${blackListPrefix}:$token")
    }

    override fun removeToken(id: Long?, token: String?, type: TokenType): Mono<Boolean> {
        return if (type == TokenType.BLACK_LIST && token != null) {
            reactiveTTLRepository.deleteValue("$blackListPrefix:$token")
        }
        else if (type == TokenType.REFRESH && id != null) {
            reactiveTTLRepository.deleteValue("$refreshPrefix:$id")
        }
        else {
            Mono.just(false)
        }
    }
}