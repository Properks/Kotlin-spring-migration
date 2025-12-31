package org.jeongmo.migration.auth.infrastructure.adapter.inbound.controller

import jakarta.validation.Valid
import org.jeongmo.migration.auth.application.dto.*
import org.jeongmo.migration.auth.application.port.inbound.AuthCommandUseCase
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
) {

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