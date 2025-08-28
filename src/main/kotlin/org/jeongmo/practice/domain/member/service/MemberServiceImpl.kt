package org.jeongmo.practice.domain.member.service

import org.jeongmo.practice.domain.member.dto.UpdateMemberInfoRequest
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.jeongmo.practice.global.error.code.MemberErrorCode
import org.jeongmo.practice.global.error.exception.MemberException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
): MemberService {

    @Transactional(readOnly = true)
    override fun getMemberInfo(username: String): Member = findMember(username)

    override fun updateMemberInfo(username: String, request: UpdateMemberInfoRequest): Member {
        val member: Member = findMember(username)
        member.nickname = request.nickname
        return member
    }

    override fun deleteMemberInfo(username: String) = memberRepository.deleteByUsername(username)

    private fun findMember(username: String): Member = memberRepository.findByUsername(username) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
}