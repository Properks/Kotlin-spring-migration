package org.jeongmo.migration.member.application.service

import org.jeongmo.migration.common.enums.member.ProviderType
import org.jeongmo.migration.member.application.dto.*
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
import org.jeongmo.migration.member.application.port.inbound.MemberCommandUseCase
import org.jeongmo.migration.member.application.port.inbound.MemberQueryUseCase
import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.domain.repository.MemberRepository
import org.namul.api.payload.error.exception.ServerApplicationException
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): MemberQueryUseCase, MemberCommandUseCase {

    private val logger = LoggerFactory.getLogger(MemberService::class.java)

    @Transactional
    override fun createMember(request: CreateMemberRequest): CreateMemberResponse {
        if (request.providerType == ProviderType.LOCAL && request.password == null) throw MemberException(MemberErrorCode.INVALID_DATA)
        val member = memberRepository.save(request.toDomain(passwordEncoder))
        return CreateMemberResponse.fromDomain(member).also { logger.info("[SUCCESS_CREATE_MEMBER] member-service") }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): MemberInfoResponse {
        val foundMember: Member? = memberRepository.findById(id)
        return MemberInfoResponse.fromDomain(
            foundMember ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        ).also { logger.info("[FIND_DOMAIN] member-service") }
    }

    @Transactional
    override fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse {
        val foundMember: Member? = memberRepository.findByUsernameAndProviderType(request.username, request.providerType)
        return foundMember?.let {
            if (passwordEncoder.matches(request.password, it.password)) {
                return VerifyMemberResponse.fromDomain(member = it).also { logger.info("[SUCCESS_VERIFY] member-service") }
            }
            else {
                throw MemberException(MemberErrorCode.INCORRECT_PASSWORD)
            }
        } ?: throw MemberException(MemberErrorCode.NOT_FOUND)
    }

    @Transactional
    override fun updateMemberInfos(id: Long, request: UpdateMemberInfoRequest): UpdateMemberInfoResponse {
        val foundMember = memberRepository.findById(id) ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        val updatedMember = if (foundMember.changeNickname(request.nickname)) memberRepository.save(member = foundMember) else foundMember
        return UpdateMemberInfoResponse.fromDomain(updatedMember).also { logger.info("[SUCCESS_UPDATE] member-service") }
    }

    @Transactional
    override fun deleteMember(id: Long) {
        try {
            if (!memberRepository.delete(id)) {
                throw MemberException(MemberErrorCode.ALREADY_DELETE)
            }
        } catch (e: ServerApplicationException) {
            logger.warn("[FAIL_DELETE] member-service | ServerApplicationException")
            logger.debug("Exception Details: ", e)
            throw e
        } catch (e: Exception) {
            logger.error("[FAIL_DELETE] member-service | Unknown Error:", e)
            logger.debug("Exception Details: ", e)
            throw MemberException(MemberErrorCode.CANNOT_DELETE, e)
        }
    }
}