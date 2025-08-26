package org.jeongmo.practice.global.security.auth.service

import org.jeongmo.practice.global.security.auth.converter.AuthConverter.toEntity
import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): AuthService {

    override fun signUp(request: AuthRequestDTO.SignUp): Member {
        return memberRepository.save(request.toEntity(passwordEncoder))
    }
}