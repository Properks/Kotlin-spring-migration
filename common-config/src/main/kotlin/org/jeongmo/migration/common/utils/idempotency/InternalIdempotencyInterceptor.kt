package org.jeongmo.migration.common.utils.idempotency

import jakarta.servlet.http.HttpServletRequest
import org.springframework.util.AntPathMatcher

class InternalIdempotencyInterceptor(
    repository: IdempotencyKeyRepository,
): IdempotencyInterceptor(repository) {

    private val matcher = AntPathMatcher()

    override fun supports(request: HttpServletRequest): Boolean {
        return matcher.match("/internal/**", request.requestURI)
    }
}