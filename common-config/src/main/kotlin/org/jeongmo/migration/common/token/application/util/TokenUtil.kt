package org.jeongmo.migration.common.token.application.util

import org.jeongmo.migration.common.token.application.dto.TokenInfoDTO
import org.jeongmo.migration.common.token.domain.model.CustomUserDetails


interface TokenUtil {

    fun createToken(userDetails: CustomUserDetails, expiration: Long): String
    fun parseToken(token: String): TokenInfoDTO
}