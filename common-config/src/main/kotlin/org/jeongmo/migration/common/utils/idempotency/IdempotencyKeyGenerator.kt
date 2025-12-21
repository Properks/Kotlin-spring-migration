package org.jeongmo.migration.common.utils.idempotency

import org.springframework.http.HttpMethod

fun interface IdempotencyKeyGenerator {
    fun generateKey(prefix: String, method: HttpMethod, endpoint: String, vararg additional: Any): String
}