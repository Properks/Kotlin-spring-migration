package org.jeongmo.migration.common.utils.idempotency

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.util.AntPathMatcher

class ScopeIdempotencyInterceptor(
    idempotencyKeyRepository: IdempotencyKeyRepository,
): IdempotencyInterceptor(idempotencyKeyRepository) {

    private val matcher = AntPathMatcher()
    private val patterns: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun supports(request: HttpServletRequest): Boolean {
        return patterns[request.method]?.any { matcher.match(it, request.requestURI) } ?: false
    }

    fun addPattern(method: HttpMethod, uri: String): ScopeIdempotencyInterceptor {
        val mutableList = patterns[method.name()] ?: mutableListOf()
        mutableList.add(uri)
        patterns[method.name()] = mutableList
        return this
    }

    fun addPatterns(method: HttpMethod, vararg uris: String): ScopeIdempotencyInterceptor {
        val mutableList = patterns[method.name()] ?: mutableListOf()
        mutableList.addAll(uris)
        patterns[method.name()] = mutableList
        return this
    }

    fun addPatterns(method: HttpMethod, uris: List<String>): ScopeIdempotencyInterceptor {
        val mutableList = patterns[method.name()] ?: mutableListOf()
        mutableList.addAll(uris)
        patterns[method.name()] = mutableList
        return this
    }
}