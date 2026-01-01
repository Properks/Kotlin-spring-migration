package org.jeongmo.migration.member.infrastructure.adapter.inbound.controller

import jakarta.validation.Valid
import org.jeongmo.migration.common.auth.annotation.LoginUserId
import org.jeongmo.migration.member.application.dto.MemberInfoResponse
import org.jeongmo.migration.member.application.dto.UpdateMemberInfoRequest
import org.jeongmo.migration.member.application.dto.UpdateMemberInfoResponse
import org.jeongmo.migration.member.application.port.inbound.MemberCommandUseCase
import org.jeongmo.migration.member.application.port.inbound.MemberQueryUseCase
import org.namul.api.payload.response.supports.DefaultResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberQueryUseCase: MemberQueryUseCase,
    private val memberCommandUseCase: MemberCommandUseCase,
) {

    @GetMapping("/infos")
    fun getMemberInfo(@LoginUserId userId: Long): DefaultResponse<MemberInfoResponse>
        = DefaultResponse.ok(memberQueryUseCase.findById(userId))

    @PatchMapping("/infos")
    fun updateMemberInfo(@LoginUserId userId: Long, @Valid @RequestBody request: UpdateMemberInfoRequest): DefaultResponse<UpdateMemberInfoResponse> =
        DefaultResponse.ok(memberCommandUseCase.updateMemberInfos(userId, request))

    @DeleteMapping
    fun deleteMember(@LoginUserId userId: Long): DefaultResponse<Unit> {
        memberCommandUseCase.deleteMember(userId)
        return DefaultResponse.noContent()
    }
}