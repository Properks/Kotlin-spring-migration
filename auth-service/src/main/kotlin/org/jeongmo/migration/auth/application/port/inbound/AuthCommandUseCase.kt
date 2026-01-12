package org.jeongmo.migration.auth.application.port.inbound

import org.jeongmo.migration.auth.application.dto.*

interface AuthCommandUseCase {

    /**
     * 회원 가입 처리 메서드
     * @param request 회원 가입 양식에 맞는 RequestBody
     * @throws AuthException 회원 가입에 실패한 경우 해당 에러 발생
     */
    fun signUp(request: SignUpRequest)

    /**
     * 로그인 처리 메서드
     * @param request 로그인 양식에 맞는 RequestBody
     * @return 로그인 처리 후 필요 데이터 반환(토큰)
     * @throws AuthException 로그인, 사용자 인증에 실패한 경우 해당 에러 발생
     */
    fun login(request: LoginRequest): LoginResponse

    /**
     * 토큰 재발급 메서드
     * @param request 토큰 재발급에 필요한 RequestBody
     * @return 재발급된 토큰과 추가 데이터
     * @throws TokenException 재발급을 위해 전달된 토큰이나 내부 데이터가 유효하지 않은 경우 발생
     */
    fun reissueToken(request: ReissueTokenRequest): ReissueTokenResponse
}