package org.jeongmo.migration.member.application.service

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.member.application.dto.*
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
import org.jeongmo.migration.member.application.port.`in`.MemberCommandUseCase
import org.jeongmo.migration.member.application.port.`in`.MemberQueryUseCase
import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.domain.repository.MemberRepository
import org.namul.api.payload.error.exception.ServerApplicationException
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): MemberQueryUseCase, MemberCommandUseCase {

    private val logger = LoggerFactory.getLogger(MemberService::class.java)

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

    override fun updateMemberInfos(id: Long, request: UpdateMemberInfoRequest): UpdateMemberInfoResponse {
        val foundMember = memberRepository.findById(id) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        val updatedMember = if (foundMember.changeNickname(request.nickname)) memberRepository.save(member = foundMember) else foundMember
        return UpdateMemberInfoResponse.fromDomain(updatedMember)
    }

    override fun deleteMember(id: Long) {
        try {
            if (!memberRepository.delete(id)) {
                throw MemberException(MemberErrorCode.ALREADY_DELETE)
            }
        } catch (e: ServerApplicationException) {
            logger.warn("ServerApplicationException 발생 ${e.message}", e)
            throw e
        } catch (e: Exception) {
            logger.error("Unknown Error 발생 ${e.message}", e)
            throw MemberException(MemberErrorCode.CANNOT_DELETE)
        }
    }
}