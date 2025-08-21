package org.jeongmo.practice.domain.auth.controller

import org.jeongmo.practice.domain.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.auth.service.AuthService
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: AuthRequestDTO.SignUp): DefaultResponse<Unit> {
        authService.signUp(request)
        return DefaultResponse.noContent()
    }
}