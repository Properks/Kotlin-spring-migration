package org.jeongmo.practice.global.security.auth.dto

object AuthResponseDTO {
    data class ReissueToken(val accessToken: String)
}