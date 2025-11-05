package org.jeongmo.migration.common.auth.annotation.resolver

import jakarta.servlet.http.HttpServletRequest
import org.jeongmo.migration.common.auth.annotation.LoginUserId
import org.jeongmo.migration.common.auth.constants.INTERNAL_SERVER_AUTH_HEADER_NAME
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginUserIdResolver: HandlerMethodArgumentResolver {

    private val logger = LoggerFactory.getLogger(LoginUserIdResolver::class.java)

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(LoginUserId::class.java) && parameter.parameterType == Long::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
        return try {
            request?.getHeader(INTERNAL_SERVER_AUTH_HEADER_NAME)?.toLong()
        } catch (e: NumberFormatException) {
            logger.warn("인증 헤더는 있지만 타입 변환에 실패했습니다.")
            null
        }
    }
}