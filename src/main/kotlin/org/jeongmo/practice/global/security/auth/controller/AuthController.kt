package org.jeongmo.practice.global.security.auth.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.global.security.auth.dto.AuthResponseDTO
import org.jeongmo.practice.global.security.auth.service.AuthService
import org.jeongmo.practice.global.security.filter.dto.LoginRequestDTO
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 API")
@RestController
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: AuthRequestDTO.SignUp): DefaultResponse<Unit> {
        authService.signUp(request)
        return DefaultResponse.noContent()
    }

    // Swagger 표시를 위한 Dummy controller 실제로는 filter에서 처리 후 컨트롤러로 전송 X
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDTO): DefaultResponse<Unit> = DefaultResponse.noContent()

    @PostMapping("/reissue")
    fun reissueToken(@RequestBody request: AuthRequestDTO.ReissueToken): DefaultResponse<AuthResponseDTO.ReissueToken> = DefaultResponse.ok(authService.reissueToken(request))
}