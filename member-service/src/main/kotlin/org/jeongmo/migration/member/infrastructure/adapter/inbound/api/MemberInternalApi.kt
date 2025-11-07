package org.jeongmo.migration.member.infrastructure.adapter.inbound.api

import org.jeongmo.migration.member.application.dto.*
import org.jeongmo.migration.member.application.port.inbound.MemberCommandUseCase
import org.jeongmo.migration.member.application.port.inbound.MemberQueryUseCase
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/api/member")
class MemberInternalApi(
    private val memberCommandUseCase: MemberCommandUseCase,
    private val memberQueryUseCase: MemberQueryUseCase,
) {

    @PostMapping
    fun createMember(@RequestBody request: CreateMemberRequest): DefaultResponse<CreateMemberResponse> =
        DefaultResponse.ok(memberCommandUseCase.createMember(request))

    @GetMapping("/{memberId}")
    fun getMember(@PathVariable("memberId") memberId: Long): DefaultResponse<MemberInfoResponse> =
        DefaultResponse.ok(memberQueryUseCase.findById(memberId))

    @PostMapping("/verify")
    fun verifyMember(@RequestBody request: VerifyMemberRequest): DefaultResponse<VerifyMemberResponse?> =
        DefaultResponse.ok(memberCommandUseCase.verifyMember(request))
}