package org.jeongmo.migration.api.gateway.security.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.namul.api.payload.code.BaseErrorCode
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class HttpResponseUtil(
    private val failureResponseWriter: FailureResponseWriter,
    private val objectMapper: ObjectMapper, // 자동으로 생성된 ObjectMapper의 설정을 그대로 사용하기 위해 의존성 주입
) {

    fun <T> writeResponse(exchange: ServerWebExchange, code: BaseErrorCode, result: T): Mono<Void> {
        val response = exchange.response
        response.setStatusCode(code.reason.httpStatus)
        response.headers.contentType = MediaType.APPLICATION_JSON

        val data = failureResponseWriter.onFailure(code.reason, result)
        val jsonData = objectMapper.writeValueAsString(data)
        return response.writeWith(Mono.just(response.bufferFactory().wrap(jsonData.toByteArray(Charsets.UTF_8))))
    }
}