package org.jeongmo.practice.domain.member.service

import org.jeongmo.practice.domain.member.dto.UpdateMemberInfoRequest
import org.jeongmo.practice.domain.member.entity.Member

interface MemberService {
    fun getMemberInfo(username: String): Member
    fun updateMemberInfo(username: String, request: UpdateMemberInfoRequest): Member
    fun deleteMemberInfo(username: String)
}