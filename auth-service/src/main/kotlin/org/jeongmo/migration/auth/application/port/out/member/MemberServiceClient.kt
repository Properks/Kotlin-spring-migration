package org.jeongmo.migration.auth.application.port.out.member

import org.jeongmo.migration.auth.application.port.out.member.dto.*

// 성공 시 데이터 반환 실패 시 에러 발생
interface MemberServiceClient {

    /**
     * 사용자 도메인과 소통하여 사용자를 생성하는 요청 (회원 가입 등의 경우)
     * @param request 사용자 생성 요청에 필요한 RequestBody
     * @return 생성된 사용자의 데이터
     * @throws AuthException 사용자 생성에 실패한 경우 해당 에러 발생
     */
    fun createMember(request: CreateMemberRequest): CreateMemberResponse

    /**
     * 사용자 도메인과 소통하여 적절한 사용자인지 확인
     * @param request 사용자 확인에 필요한 RequestBody
     * @return 인증 완료된 사용자의 데이터
     * @throws AuthException 사용자 인증에 실패한 경우 해당 에러 발생
     */
    fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse
}