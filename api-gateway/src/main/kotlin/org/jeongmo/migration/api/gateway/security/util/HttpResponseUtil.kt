package org.jeongmo.migration.api.gateway.security.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultBaseSuccessCode
import org.namul.api.payload.response.supports.DefaultResponse
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class HttpResponseUtil(
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper, // 자동으로 생성된 ObjectMapper의 설정을 그대로 사용하기 위해 의존성 주입
) {

    fun writeResponse(exchange: ServerWebExchange, code: DefaultBaseErrorCode, exception: Exception?): Mono<Void> {
        val response = exchange.response
        response.setStatusCode(code.httpStatus)
        response.headers.contentType = MediaType.APPLICATION_JSON

        val data = failureResponseWriter.onFailure(exception, code)
        val jsonData = objectMapper.writeValueAsString(data)
        return response.writeWith(Mono.just(response.bufferFactory().wrap(jsonData.toByteArray(Charsets.UTF_8))))
    }

    fun <T> writeResponse(exchange: ServerWebExchange, code: DefaultBaseSuccessCode, result: T?): Mono<Void> {
        val response = exchange.response
        response.setStatusCode(HttpStatus.OK)
        response.headers.contentType = MediaType.APPLICATION_JSON

        val data = DefaultResponse.ok(result)
        val jsonData = objectMapper.writeValueAsString(data)
        return response.writeWith(Mono.just(response.bufferFactory().wrap(jsonData.toByteArray(Charsets.UTF_8))))
    }
}