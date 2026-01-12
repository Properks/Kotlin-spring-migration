package org.jeongmo.migration.common.token.application.dto

import org.jeongmo.migration.common.token.application.constants.TokenType
import org.springframework.security.core.GrantedAuthority

data class TokenInfoDTO(
    /**
     * 사용자 ID
     */
    val id: String,

    /**
     * 토큰 형식 (REFRESH, ACCESS..)
     */
    val type: TokenType,

    /**
     * 사용자 이름
     */
    val username: String,

    /**
     * 사용자 권한
     */
    val roles: Collection<GrantedAuthority>
)
