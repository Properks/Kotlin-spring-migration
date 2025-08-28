package org.jeongmo.practice.global.annotation.resolver

import jakarta.servlet.http.HttpServletRequest
import org.jeongmo.practice.global.annotation.AuthenticatedMember
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.springframework.core.MethodParameter
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthenticatedMemberResolver(
    private val securityContextRepository: SecurityContextRepository,
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticatedMember::class.java) && parameter.parameterType == String::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authentication: Authentication = securityContextRepository.loadDeferredContext(webRequest.getNativeRequest(HttpServletRequest::class.java)).get().authentication
        return authentication.principal
    }
}