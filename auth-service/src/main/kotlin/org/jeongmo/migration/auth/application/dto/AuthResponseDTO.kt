package org.jeongmo.migration.auth.application.dto

import java.time.LocalDateTime

data class LoginResponse(val accessToken: String, val refreshToken: String, val role: List<String>, val loginTime: LocalDateTime)
data class ReissueTokenResponse(val accessToken: String)