package org.jeongmo.migration.auth.infrastructure.util

import jakarta.servlet.http.HttpServletRequest

/**
 * 토큰 추출 인터페이스
 */
fun interface TokenExtractor {

    /**
     * 토큰을 추출하는 메서드
     * @param httpServletRequest HttpServletRequest 요청 정보
     * @return 추출에 성공하면 토큰, 실패하면 null
     */
    fun extractToken(httpServletRequest: HttpServletRequest): String?
}