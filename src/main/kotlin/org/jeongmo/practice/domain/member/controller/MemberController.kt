package org.jeongmo.practice.domain.member.controller

import org.jeongmo.practice.domain.member.converter.toFindMemberInfo
import org.jeongmo.practice.domain.member.converter.toUpdateMemberInfo
import org.jeongmo.practice.domain.member.dto.*
import org.jeongmo.practice.domain.member.service.MemberService
import org.jeongmo.practice.global.annotation.AuthenticatedMember
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService,
) {

    @GetMapping("/infos")
    fun getMemberInfo(@AuthenticatedMember username: String): DefaultResponse<FindMemberInfoResponse> =
        DefaultResponse.ok(memberService.getMemberInfo(username).toFindMemberInfo())

    @PatchMapping("/infos")
    fun updateMemberInfo(@AuthenticatedMember username: String, @RequestBody request: UpdateMemberInfoRequest): DefaultResponse<UpdateMemberInfoResponse> =
        DefaultResponse.ok(memberService.updateMemberInfo(username, request).toUpdateMemberInfo())

    @DeleteMapping
    fun deleteMember(@AuthenticatedMember username: String): DefaultResponse<Void> {
        memberService.deleteMemberInfo(username)
        return DefaultResponse.noContent()
    }
}