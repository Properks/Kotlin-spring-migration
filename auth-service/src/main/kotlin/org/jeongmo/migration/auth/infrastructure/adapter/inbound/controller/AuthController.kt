package org.jeongmo.migration.auth.infrastructure.adapter.inbound.controller

import org.jeongmo.migration.auth.application.dto.*
import org.jeongmo.migration.auth.application.port.`in`.AuthCommandUseCase
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authCommandUseCase: AuthCommandUseCase,
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): DefaultResponse<Unit> {
        authCommandUseCase.signUp(request)
        return DefaultResponse.noContent()
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): DefaultResponse<LoginResponse> {
        val response = authCommandUseCase.login(request)
        return DefaultResponse.ok(response)
    }

    @PostMapping("/reissue")
    fun reissueToken(@RequestBody request: ReissueTokenRequest): DefaultResponse<ReissueTokenResponse> {
        val response = authCommandUseCase.reissueToken(request)
        return DefaultResponse.ok(response)
    }
}