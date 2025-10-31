package org.jeongmo.migration.member.application.service

import org.jeongmo.migration.member.application.dto.*
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
import org.jeongmo.migration.member.application.port.`in`.MemberCommandUseCase
import org.jeongmo.migration.member.application.port.`in`.MemberQueryUseCase
import org.jeongmo.migration.member.domain.enum.ProviderType
import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.domain.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): MemberQueryUseCase, MemberCommandUseCase {

    override fun createMember(request: CreateMemberRequest): CreateMemberResponse {
        if (request.providerType == ProviderType.LOCAL && request.password == null) throw MemberException(MemberErrorCode.INVALID_DATA)
        val member = memberRepository.save(request.toDomain(passwordEncoder))
        return CreateMemberResponse.fromDomain(member)
    }

    override fun findById(id: Long): MemberInfoResponse {
        val foundMember: Member? = memberRepository.findById(id)
        return MemberInfoResponse.fromDomain(
            foundMember ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        )
    }

    override fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse? {
        val foundMember: Member? = memberRepository.findByUsernameAndProviderType(request.username, request.providerType)
        return foundMember?.let {
            if (passwordEncoder.matches(request.password, it.password)) {
                return VerifyMemberResponse.fromDomain(member = it)
            }
            else {
                throw MemberException(MemberErrorCode.INCORRECT_PASSWORD)
            }
        } ?: throw MemberException(MemberErrorCode.NOT_FOUND)
    }
}