package org.jeongmo.migration.auth.application.service

import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails


interface TokenAuthService {

    /**
     * Access Token 생성 메서드
     * @param userDetails 사용자의 인증 정보
     * @return 생성된 토큰
     */
    fun createAccessToken(userDetails: CustomUserDetails): String

    /**
     * Refresh Token 생성 메서드
     * @param userDetails 사용자의 인증 정보
     * @return 생성된 토큰
     */
    fun createRefreshToken(userDetails: CustomUserDetails): String

    /**
     * 토큰으로 부터 데이터를 가져오는 메서드
     * @param token 데이터를 가져올 토큰
     * @return 토큰으로부터 추출한 데이터
     * @throws TokenException 전달된 토큰이 유효하지 않거나 토큰의 형식이 미리 정의된 형식이 아닌 경우 발생
     */
    fun getTokenInfo(token: String): TokenInfoDTO
}