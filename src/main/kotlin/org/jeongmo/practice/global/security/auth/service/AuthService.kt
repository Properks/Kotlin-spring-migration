package org.jeongmo.practice.global.security.auth.service

import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.member.entity.Member

interface AuthService {
    fun signUp(request: AuthRequestDTO.SignUp): Member
}