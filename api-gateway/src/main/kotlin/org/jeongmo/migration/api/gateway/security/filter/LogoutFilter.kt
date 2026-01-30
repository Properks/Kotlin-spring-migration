package org.jeongmo.migration.api.gateway.security.filter

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.jeongmo.migration.common.token.domain.repository.TokenRepository
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseSuccessCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.http.HttpMethod
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class LogoutFilter(
    logoutUrl: String,
    private val tokenUtil: TokenUtil,
    private val tokenRepository: TokenRepository,
    private val httpResponseUtil: HttpResponseUtil,
): WebFilter{

    private val matcher = PathPatternParserServerWebExchangeMatcher(logoutUrl, HttpMethod.POST)
    private val authorizationHeader = "Authorization"
    private val prefix = "Bearer "

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return matcher.matches(exchange).flatMap { matchResult ->
            if (matchResult.isMatch) {
                val token = extractToken(exchange) ?: return@flatMap Mono.error(TokenException(TokenErrorCode.FAIL_READ_TOKEN))
                try {
                    val tokenInfo = tokenUtil.parseToken(token)
                    return@flatMap if (tokenRepository.saveToken(tokenInfo.id.toLong(), token, TokenType.BLACK_LIST)) {
                        httpResponseUtil.writeResponse(exchange, DefaultResponseSuccessCode.OK, "로그아웃 성공")
                    }
                    else {
                        Mono.error(TokenException(TokenErrorCode.TOKEN_NOT_VALID))
                    }
                } catch (e: ServerApplicationException) {
                    return@flatMap httpResponseUtil.writeResponse(
                        exchange,
                        e.code as DefaultBaseErrorCode,
                        e
                    )
                }
            }
            else {
                chain.filter(exchange)
            }
        }
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        val header = (exchange.request.headers[authorizationHeader] ?: return null)[0]

        return if (header.isNotEmpty() && header.startsWith(prefix)) {
            header.substring(prefix.length)
        } else {
            null
        }
    }

}