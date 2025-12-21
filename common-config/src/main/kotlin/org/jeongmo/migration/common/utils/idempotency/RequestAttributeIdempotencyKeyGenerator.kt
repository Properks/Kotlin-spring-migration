package org.jeongmo.migration.common.utils.idempotency

import org.springframework.http.HttpMethod
import org.springframework.util.DigestUtils


class RequestAttributeIdempotencyKeyGenerator: IdempotencyKeyGenerator {

    override fun generateKey(prefix: String, method: HttpMethod, endpoint: String, vararg additional: Any): String {
        val additionalString = if (additional.isNotEmpty()) {
            additional.joinToString(separator = ":")
        } else {
            null
        }
        val key = additionalString?.let { "$method:$endpoint:${it}" } ?: "$method:$endpoint"
        val hash = DigestUtils.md5DigestAsHex(key.toByteArray(Charsets.UTF_8))
        return "$prefix:$hash"
    }
}