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
        when (idempotencyKeyRepository.setStatusIfAbsent(key, IdempotencyKeyStatus.PROCESSING)) {
            null -> {
                return true
            }
            IdempotencyKeyStatus.COMPLETE -> {
                throw IdempotencyException(IdempotencyErrorCode.ALREADY_COMPLETE)
            }
            IdempotencyKeyStatus.PROCESSING -> {
                throw IdempotencyException(IdempotencyErrorCode.ALREADY_PROCESSING)
            }
        }
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
            idempotencyKeyRepository.deleteKey(key = key)
        } ?: idempotencyKeyRepository.setStatus(key = key, status = IdempotencyKeyStatus.COMPLETE)
    }

    open fun supports(request: HttpServletRequest): Boolean {
        return true
    }

    private fun getIdempotencyKey(request: HttpServletRequest): String? {
        return request.getHeader(IDEMPOTENCY_KEY_NAME)
    }
}