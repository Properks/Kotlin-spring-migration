package org.jeongmo.practice.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.practice.global.util.HttpResponseWriter
import org.jeongmo.practice.global.util.logger
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO
import org.namul.api.payload.error.exception.ServerApplicationException
import java.lang.Exception

class JsonResponseFilterExceptionHandler(
    private val httpResponseWriter: HttpResponseWriter<DefaultResponseSuccessReasonDTO, DefaultResponseErrorReasonDTO>,
): FilterExceptionHandler {

    private val logger = logger()

    override fun handleServerApplicationException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: ServerApplicationException
    ) {
        val reasonDTO: ErrorReasonDTO = exception.errorReason
        if (reasonDTO is DefaultResponseErrorReasonDTO) {
            logError(exception, reasonDTO.message)
            this.writeException(
                exception = exception,
                response = response,
                reasonDTO = reasonDTO
            )
        }
        else {
            handleException(request, response, exception)
        }
    }

    override fun handleException(request: HttpServletRequest, response: HttpServletResponse, exception: Exception) {
        logError(exception, exception.message ?: "Unknown")
        val reasonDTO: DefaultResponseErrorReasonDTO = DefaultResponseErrorCode._UNAUTHORIZED.reason

        this.writeException(
            exception = exception,
            response = response,
            reasonDTO = reasonDTO,
        )
    }

    private fun logError(exception: Exception, message: String) {
        if (exception is ServerApplicationException) {
            logger.warn("Filter Error(${exception::class.simpleName}): $message")
        }
        else {
            logger.error("Filter Error(${exception::class.simpleName}): $message")
        }
    }

    private fun writeException(
        response: HttpServletResponse,
        exception: Exception,
        reasonDTO: DefaultResponseErrorReasonDTO,
    ) {
        httpResponseWriter.writeErrorResponse(
            response,
            reasonDTO.httpStatus,
            reasonDTO,
            result = exception.message
        )
    }
}