package org.jeongmo.migration.auth.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:NotBlank(message = "사용자 이름이 비어있습니다.")
    val username: String,

    @field:Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
    val password: String,

    @field:NotBlank(message = "별명이 비어있습니다.")
    val nickname: String
)
data class LoginRequest(
    @field:NotBlank(message = "사용자 이름이 비어있습니다.")
    val username: String,

    @field:Size(min = 8, message = "비밀번호는 8자 이상이여야 합니다.")
    val password: String
)
data class ReissueTokenRequest(
    @field:NotBlank(message = "토큰이 비어있거나 공백입니다.")
    val refreshToken: String
)