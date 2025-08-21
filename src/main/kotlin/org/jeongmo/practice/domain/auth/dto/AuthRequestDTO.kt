package org.jeongmo.practice.domain.auth.dto

object AuthRequestDTO {
    data class SignUp(val username: String, val password: String, val nickname: String)
}