package org.jeongmo.migration.member.application.service

import org.jeongmo.migration.member.application.dto.MemberInfoResponse
import org.jeongmo.migration.member.application.error.code.MemberErrorCode
import org.jeongmo.migration.member.application.error.exception.MemberException
import org.jeongmo.migration.member.application.port.inbound.MemberQueryUseCase
import org.jeongmo.migration.member.domain.model.Member
import org.jeongmo.migration.member.domain.repository.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberQueryService(
    private val memberRepository: MemberRepository,
): MemberQueryUseCase {

    private val logger = LoggerFactory.getLogger(MemberQueryService::class.java)

    @Transactional(readOnly = true)
    override fun findById(id: Long): MemberInfoResponse {
        val foundMember: Member? = memberRepository.findById(id)
        return MemberInfoResponse.fromDomain(
            foundMember ?: throw MemberException(MemberErrorCode.NOT_FOUND)
        ).also { logger.info("[FIND_DOMAIN] member-service") }
    }
}