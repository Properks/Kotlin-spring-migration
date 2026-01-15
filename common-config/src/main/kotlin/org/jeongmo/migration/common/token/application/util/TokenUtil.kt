package org.jeongmo.migration.common.token.application.util

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails
import org.jeongmo.migration.common.token.application.error.exception.TokenException


interface TokenUtil {

    /**
     * 토큰 생성 메서드
     * @param userDetails 사용자 정보가 담긴 CustomUserDetails
     * @param expiration 토큰의 유효 기간, 단위: milli second
     * @param type 토큰 형식 (ACCESS, REFRESH...)
     * @return 생성된 토큰
     */
    fun createToken(userDetails: CustomUserDetails, expiration: Long, type: TokenType): String

    /**
     * 토큰으로부터 정보 가져오기
     * @param token 정보를 가져오기 위한 토큰
     * @return 토큰 정보 DTO
     * @throws TokenException 전달된 토큰이 유효하지 않거나 미리 지정된 토큰의 형식이 아닌 경우
     */
    fun parseToken(token: String): TokenInfoDTO
}