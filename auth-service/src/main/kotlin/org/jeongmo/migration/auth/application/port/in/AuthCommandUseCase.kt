package org.jeongmo.migration.auth.application.port.`in`

import org.jeongmo.migration.auth.application.dto.*

interface AuthCommandUseCase {
    fun signUp(request: SignUpRequest)
    fun login(request: LoginRequest): LoginResponse
    fun reissueToken(request: ReissueTokenRequest): ReissueTokenResponse
}