package org.jeongmo.migration.auth.application.dto

import org.springframework.security.core.GrantedAuthority
import java.time.LocalDateTime

data class LoginResponse(val accessToken: String, val refreshToken: String?, val role: List<String>, val loginTime: LocalDateTime)
data class ReissueTokenResponse(val accessToken: String)
data class AuthorizeResponse(val id: Long, val roles: Collection<GrantedAuthority>)