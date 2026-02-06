package org.jeongmo.migration.auth.infrastructure.util

import jakarta.servlet.http.HttpServletRequest

abstract class HeaderTokenExtractor: TokenExtractor {

    final override fun extractToken(httpServletRequest: HttpServletRequest): String? {
        val header = httpServletRequest.getHeader(getHeaderName()) ?: return null
        return if (header.startsWith(getTokenPrefix())) {header.substring(getTokenPrefix().length)} else {null}
    }

    /**
     * 확인할 헤더의 이름을 반환하는 메서드
     * @return 헤더의 이름
     */
    abstract fun getHeaderName(): String

    /**
     * 토큰의 prefix로 토큰을 제외한 앞부분 전체 ex) "Bearer {token}"이면 "Bearer "
     * @return 토큰의 접두사
     */
    abstract fun getTokenPrefix(): String
}