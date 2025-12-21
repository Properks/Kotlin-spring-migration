package org.jeongmo.migration.common.utils.idempotency

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception

open class IdempotencyInterceptor(
    private val idempotencyKeyRepository: IdempotencyKeyRepository,
): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (!supports(request)) {
            return true
        }
        val key = getIdempotencyKey(request) ?: throw IdempotencyException(IdempotencyErrorCode.NOT_FOUND_KEY_IN_HEADER)
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
        if (!supports(request)) {
            return
        }
        val key = getIdempotencyKey(request) ?: throw IdempotencyException(IdempotencyErrorCode.NOT_FOUND_KEY_IN_HEADER)
        ex?.let {
            idempotencyKeyRepository.setStatus(key = key, status = IdempotencyKeyStatus.FAIL)
        } ?: idempotencyKeyRepository.setStatus(key = key, status = IdempotencyKeyStatus.COMPLETE)
    }

    open fun supports(request: HttpServletRequest): Boolean {
        return true
    }

    private fun getIdempotencyKey(request: HttpServletRequest): String? {
        return request.getHeader(IDEMPOTENCY_KEY_NAME)
    }
}