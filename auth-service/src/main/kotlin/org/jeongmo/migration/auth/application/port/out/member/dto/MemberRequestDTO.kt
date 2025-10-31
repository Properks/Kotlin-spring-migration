package org.jeongmo.migration.auth.application.port.out.member.dto

import org.jeongmo.migration.auth.application.port.out.member.enums.ProviderType
import org.jeongmo.migration.auth.application.port.out.member.enums.Role

data class CreateMemberRequest(
    val username: String,
    val password: String?,
    val providerType: ProviderType,
    val nickname: String,
    val role: Role,
) {
}

data class VerifyMemberRequest(
    val username: String,
    val password: String,
    val providerType: ProviderType,
) {

}