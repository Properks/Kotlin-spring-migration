package org.jeongmo.migration.common.utils.api.payload.supports

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.migration.common.utils.api.payload.HttpServletErrorResponseWriter
import org.namul.api.payload.code.supports.DefaultBaseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.http.MediaType

class HttpServletDefaultErrorResponseWriter(
    private val failureResponseWriter: FailureResponseWriter<DefaultBaseErrorCode>,
    private val objectMapper: ObjectMapper,
): HttpServletErrorResponseWriter<DefaultBaseErrorCode> {

    override fun writeResponse(response: HttpServletResponse, errorCode: DefaultBaseErrorCode, e: Exception?) {
        response.status = errorCode.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val responseData = failureResponseWriter.onFailure(e ?: ServerApplicationException(errorCode), errorCode)
        objectMapper.writeValue(response.outputStream, responseData)
    }
}