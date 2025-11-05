package org.jeongmo.migration.api.gateway.security.filter

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.application.error.code.TokenErrorCode
import org.jeongmo.migration.common.token.application.error.exception.TokenException
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

abstract class TokenAuthenticationFilter(
    private val tokenUtil: TokenUtil,
    private val httpResponseUtil: HttpResponseUtil,
): WebFilter  {

    private val logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)
    private val userIdHeader = "X-User-Id"

    final override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val token = extractToken(exchange) ?: return chain.filter(exchange)
        val info = try {
            tokenUtil.parseToken(token)
        } catch (e: TokenException) {
            // tokenUtil에서 에러 로깅
            return httpResponseUtil.writeResponse(exchange, e.code, e.message)
        } catch (e: Exception) {
            logger.error("Token Unkown Error", e)
            return httpResponseUtil.writeResponse(exchange, TokenErrorCode.TOKEN_NOT_VALID, e.message)
        }

        val modifiedExchange = addHeader(exchange, info)
        return chain.filter(modifiedExchange)
            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(successfulAuthentication(info)))
    }

    protected abstract fun extractToken(exchange: ServerWebExchange): String?

    private fun addHeader(exchange: ServerWebExchange, tokenInfo: TokenInfoDTO): ServerWebExchange {
        val request = exchange.request.mutate()
            .header(userIdHeader, tokenInfo.id)
            .build()
        return exchange.mutate().request(request).build()

    }

    private fun successfulAuthentication(tokenInfo: TokenInfoDTO): Authentication = UsernamePasswordAuthenticationToken(tokenInfo.id, "", tokenInfo.roles)
}