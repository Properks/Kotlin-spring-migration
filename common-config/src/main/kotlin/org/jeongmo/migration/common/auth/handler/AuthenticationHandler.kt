package org.jeongmo.migration.common.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class AuthenticationHandler(
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper,
): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        val code = DefaultResponseErrorCode.UNAUTHORIZED
        response.status = code.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val responseData = failureResponseWriter.onFailure(authException, code)
        objectMapper.writeValue(response.outputStream, responseData)
    }
}