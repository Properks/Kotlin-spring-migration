package org.jeongmo.migration.common.utils.api.payload

import jakarta.servlet.http.HttpServletResponse
import org.namul.api.payload.code.BaseErrorCode

/**
 * HttpServlet 환경에서 에러 응답 작성을 돕는 인터페이스
 */
fun interface HttpServletErrorResponseWriter<T: BaseErrorCode> {
    fun writeResponse(response: HttpServletResponse, errorCode: T, e: Exception?)
}