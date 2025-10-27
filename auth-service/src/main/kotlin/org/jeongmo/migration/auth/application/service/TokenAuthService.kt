package org.jeongmo.migration.auth.application.service

import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails


interface TokenAuthService {
    fun createAccessToken(userDetails: CustomUserDetails): String
    fun createRefreshToken(userDetails: CustomUserDetails): String
    fun getTokenInfo(token: String): TokenInfoDTO
}