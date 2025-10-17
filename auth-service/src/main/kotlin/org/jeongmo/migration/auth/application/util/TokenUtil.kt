package org.jeongmo.migration.auth.application.util

import org.jeongmo.migration.auth.application.dto.TokenInfoDTO
import org.jeongmo.migration.auth.domain.model.CustomUserDetails

interface TokenUtil {

    fun createToken(userDetails: CustomUserDetails, expiration: Long): String
    fun parseToken(token: String): TokenInfoDTO
}