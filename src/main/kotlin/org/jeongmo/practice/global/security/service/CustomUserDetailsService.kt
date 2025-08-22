package org.jeongmo.practice.global.security.service

import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.jeongmo.practice.global.error.code.MemberErrorCode
import org.jeongmo.practice.global.error.exception.MemberException
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val member: Member = memberRepository.findByUsername(username!!) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        return CustomUserDetails(member)
    }
}