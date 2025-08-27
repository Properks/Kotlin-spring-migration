package org.jeongmo.practice.global.security.auth.service

import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.global.security.auth.dto.AuthResponseDTO

interface AuthService {
    fun signUp(request: AuthRequestDTO.SignUp): Member
    fun reissueToken(request: AuthRequestDTO.ReissueToken): AuthResponseDTO.ReissueToken
}