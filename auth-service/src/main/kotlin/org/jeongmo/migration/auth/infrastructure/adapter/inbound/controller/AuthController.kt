package org.jeongmo.migration.auth.infrastructure.adapter.inbound.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.jeongmo.migration.auth.application.dto.*
import org.jeongmo.migration.auth.application.error.code.AuthErrorCode
import org.jeongmo.migration.auth.application.error.exception.AuthException
import org.jeongmo.migration.auth.application.port.inbound.AuthCommandUseCase
import org.jeongmo.migration.auth.infrastructure.util.HeaderTokenExtractor
import org.jeongmo.migration.auth.infrastructure.util.TokenExtractor
import org.namul.api.payload.response.supports.DefaultResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authCommandUseCase: AuthCommandUseCase,
    private val tokenExtractor: TokenExtractor,
) {

    @PostMapping
    fun authorize(httpServletRequest: HttpServletRequest):DefaultResponse<AuthorizeResponse> {
        val response = authCommandUseCase.authorize(
            tokenExtractor.extractToken(httpServletRequest) ?: throw AuthException(AuthErrorCode.UNAUTHORIZED_DATA)
        )
        return DefaultResponse.ok(response)
    }

    @PostMapping("/logout")
    fun logout(httpServletRequest: HttpServletRequest): DefaultResponse<Unit> {
        authCommandUseCase.logout(
            token = tokenExtractor.extractToken(httpServletRequest) ?: throw AuthException(AuthErrorCode.UNAUTHORIZED_DATA)
        )
        return DefaultResponse.noContent()
    }

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody request: SignUpRequest): DefaultResponse<Unit> {
        authCommandUseCase.signUp(request)
        return DefaultResponse.noContent()
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): DefaultResponse<LoginResponse> {
        val response = authCommandUseCase.login(request)
        return DefaultResponse.ok(response)
    }

    @PostMapping("/reissue")
    fun reissueToken(@Valid @RequestBody request: ReissueTokenRequest): DefaultResponse<ReissueTokenResponse> {
        val response = authCommandUseCase.reissueToken(request)
        return DefaultResponse.ok(response)
    }
}