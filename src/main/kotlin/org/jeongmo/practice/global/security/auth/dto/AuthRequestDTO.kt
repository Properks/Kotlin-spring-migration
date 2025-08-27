package org.jeongmo.practice.global.security.auth.dto

object AuthRequestDTO {
    data class SignUp(val username: String, val password: String, val nickname: String)
    data class ReissueToken(val refreshToken: String)
}