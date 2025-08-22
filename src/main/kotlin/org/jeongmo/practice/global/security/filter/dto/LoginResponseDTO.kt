package org.jeongmo.practice.global.security.filter.dto

import java.time.LocalDateTime

data class LoginResponseDTO(val accessToken: String, val refreshToken: String, val role: String, val loginTime: LocalDateTime)