package org.jeongmo.migration.member.application.port.`in`

import org.jeongmo.migration.member.application.dto.MemberInfoResponse

interface MemberQueryUserCase {
    fun findById(id: Long): MemberInfoResponse
}