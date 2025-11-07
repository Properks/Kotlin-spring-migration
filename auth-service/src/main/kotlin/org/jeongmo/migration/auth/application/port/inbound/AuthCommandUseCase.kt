package org.jeongmo.migration.auth.application.port.inbound

import org.jeongmo.migration.auth.application.dto.*

interface AuthCommandUseCase {
    fun signUp(request: SignUpRequest)
    fun login(request: LoginRequest): LoginResponse
    fun reissueToken(request: ReissueTokenRequest): ReissueTokenResponse
}