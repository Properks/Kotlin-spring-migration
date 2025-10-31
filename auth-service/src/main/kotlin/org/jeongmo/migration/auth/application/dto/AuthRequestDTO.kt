package org.jeongmo.migration.auth.application.dto

data class SignUpRequest(val username: String, val password: String, val nickname: String)
data class LoginRequest(val username: String, val password: String)
data class ReissueTokenRequest(val refreshToken: String)