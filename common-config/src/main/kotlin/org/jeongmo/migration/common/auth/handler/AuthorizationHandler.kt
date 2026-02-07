package org.jeongmo.migration.common.auth.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.code.supports.DefaultResponseErrorCode
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class AuthorizationHandler(
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper,
): AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        val code = DefaultResponseErrorCode.FORBIDDEN
        response.status = code.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val responseData = failureResponseWriter.onFailure(accessDeniedException, code)
        objectMapper.writeValue(response.outputStream, responseData)
    }
}