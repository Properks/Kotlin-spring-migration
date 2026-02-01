package org.jeongmo.migration.api.gateway.security.filter

import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.token.application.util.TokenUtil
import org.jeongmo.migration.common.token.domain.repository.ReactiveTokenRepository
import org.springframework.web.server.ServerWebExchange

class HeaderTokenAuthenticationFilter(
    tokenUtil: TokenUtil,
    httpResponseUtil: HttpResponseUtil,
    reactiveTokenRepository: ReactiveTokenRepository,
): TokenAuthenticationFilter(tokenUtil, httpResponseUtil, reactiveTokenRepository) {

    private val tokenHeader = "Authorization"
    private val tokenPrefix = "Bearer "

    override fun extractToken(exchange: ServerWebExchange): String? {
        val header = exchange.request.headers[tokenHeader] ?: return null
        return if (header.isEmpty() || !header[0].startsWith(tokenPrefix, false)) {
            null
        }
        else {
            header[0].substring(tokenPrefix.length)
        }
    }
}