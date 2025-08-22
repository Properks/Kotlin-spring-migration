package org.jeongmo.practice.global.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO
import org.namul.api.payload.writer.FailureResponseWriter
import org.namul.api.payload.writer.SuccessResponseWriter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class JsonHttpResponseWriter(
    private val successResponseWriter: SuccessResponseWriter<DefaultResponseSuccessReasonDTO>,
    private val failureResponseWriter: FailureResponseWriter<DefaultResponseErrorReasonDTO>,
): HttpResponseWriter<DefaultResponseSuccessReasonDTO, DefaultResponseErrorReasonDTO> {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    override fun writeSuccessResponse(httpServletResponse: HttpServletResponse, httpStatus: HttpStatus, reasonDTO: DefaultResponseErrorReasonDTO, result: Any?) {
        this.writeResponse(httpServletResponse, reasonDTO.httpStatus, failureResponseWriter.onFailure(reasonDTO, result))
    }

    override fun writeSuccessResponse(httpServletResponse: HttpServletResponse, httpStatus: HttpStatus, reasonDTO: DefaultResponseSuccessReasonDTO, result: Any?) {
        this.writeResponse(httpServletResponse, reasonDTO.httpStatus, successResponseWriter.onSuccess(reasonDTO, result))
    }

    private fun <T : Serializable> writeResponse(httpServletResponse: HttpServletResponse, httpStatus: HttpStatus, responseValue: T) {
        httpServletResponse.status = httpStatus.value()
        httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
        objectMapper.writeValue(httpServletResponse.outputStream, responseValue)
    }
}