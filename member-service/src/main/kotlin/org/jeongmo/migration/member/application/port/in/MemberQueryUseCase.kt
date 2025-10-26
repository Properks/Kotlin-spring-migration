package org.jeongmo.migration.member.application.port.`in`

import org.jeongmo.migration.member.application.dto.MemberInfoResponse

interface MemberQueryUseCase {
    fun findById(id: Long): MemberInfoResponse
}