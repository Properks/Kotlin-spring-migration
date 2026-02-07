package org.jeongmo.migration.api.gateway.security.filter

import org.jeongmo.migration.api.gateway.security.port.out.auth.AuthApiGateway
import org.jeongmo.migration.api.gateway.security.util.HttpResponseUtil
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ID_NAME
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_ROLE_NAME
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationFilter(
    private val authApiGateway: AuthApiGateway,
    private val httpResponseUtil: HttpResponseUtil,
): GlobalFilter, Ordered {

    private val order: Int = 0

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val authorizeHeader = exchange.request.headers[authApiGateway.authorizeHeaderName]
        return authorizeHeader?.let {
            if (it.isNotEmpty()) {
                authApiGateway.authorize(it[0])
                    .flatMap { authInfo ->
                        val modifiedExchange: ServerWebExchange = addHeader(exchange, authInfo.id.toString(), authInfo.roles.joinToString(", "))
                        chain.filter(modifiedExchange)
                    }
                    .onErrorResume { e ->
                        httpResponseUtil.writeResponse(exchange, DefaultResponseErrorCode.UNAUTHORIZED, ServerApplicationException(DefaultResponseErrorCode.UNAUTHORIZED, e))
                    }
            }
            else {
                chain.filter(exchange)
            }
        } ?: chain.filter(exchange)
    }

    override fun getOrder(): Int = order

    private fun addHeader(exchange: ServerWebExchange, id: String, roles: String): ServerWebExchange {
        val request = exchange.request.mutate()
            .header(INTERNAL_SERVER_AUTH_ID_NAME, id)
            .header(INTERNAL_SERVER_AUTH_ROLE_NAME, roles)
            .build()
        return exchange.mutate().request(request).build()

    }
}