package org.jeongmo.migration.common.utils.idempotency

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception

class IdempotencyInterceptor(
    private val idempotencyKeyRepository: IdempotencyKeyRepository,
):HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val key = getIdempotencyKey(request)
        val foundStatus = idempotencyKeyRepository.getStatus(key)
        when (foundStatus) {
            null, IdempotencyKeyStatus.FAIL -> {
                idempotencyKeyRepository.setStatus(key, IdempotencyKeyStatus.PROCESSING)
            }
            IdempotencyKeyStatus.COMPLETE -> {
                throw IdempotencyException(IdempotencyErrorCode.ALREADY_COMPLETE)
            }
            IdempotencyKeyStatus.PROCESSING -> {
                throw IdempotencyException(IdempotencyErrorCode.ALREADY_PROCESSING)
            }
        }
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        val key = getIdempotencyKey(request)
        ex?.let {
            idempotencyKeyRepository.setStatus(key = key, status = IdempotencyKeyStatus.FAIL)
        } ?: idempotencyKeyRepository.setStatus(key = key, status = IdempotencyKeyStatus.COMPLETE)
    }

    private fun getIdempotencyKey(request: HttpServletRequest): String {
        return request.getHeader(IDEMPOTENCY_KEY_NAME)
    }
}