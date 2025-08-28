package org.jeongmo.practice.domain.member.converter

import org.jeongmo.practice.domain.member.dto.FindMemberInfoResponse
import org.jeongmo.practice.domain.member.dto.UpdateMemberInfoResponse
import org.jeongmo.practice.domain.member.entity.Member

fun Member.toFindMemberInfo() = FindMemberInfoResponse(this.username, this.nickname, this.providerType, this.role, this.createdAt)
fun Member.toUpdateMemberInfo() = UpdateMemberInfoResponse(this.username, this.nickname, this.updatedAt)