package org.jeongmo.migration.auth.infrastructure.adapter.out.member.api

import org.jeongmo.migration.auth.application.error.code.AuthErrorCode
import org.jeongmo.migration.auth.application.error.exception.AuthException
import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.*
import org.namul.api.payload.response.DefaultResponse
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Component
class MemberApiGateway(
    private val memberWebClient: WebClient,
): MemberServiceClient {
    private val logger = LoggerFactory.getLogger(MemberApiGateway::class.java)

    override fun createMember(request: CreateMemberRequest): CreateMemberResponse {
        val type = object: ParameterizedTypeReference<DefaultResponse<CreateMemberResponse?>>() {}
        val response = sendRequest("/internal/api/member", request, type)

        return response?.result ?: run {
            logger.warn("[FAIL_API] auth | Fail api call to member service (sign-up request)")
            throw AuthException(AuthErrorCode.FAIL_SIGN_UP)
        }
    }

    override fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse {
        val type = object: ParameterizedTypeReference<DefaultResponse<VerifyMemberResponse?>>() {}
        val response = sendRequest("/internal/api/member/verify", request, type)

        return response?.result ?: run {
            logger.warn("[FAIL_API] auth | Fail api call to member service (login request)")
            throw AuthException(AuthErrorCode.FAIL_TO_VERIFY)
        }
    }

    /**
     * @return 성공 시 responseType 반환, 실패 시 null 반환
     */
    private fun <T> sendRequest(uri: String, request: Any, responseType: ParameterizedTypeReference<T>): T? =
        try {
            memberWebClient.post()
                .uri(uri)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .block(Duration.ofSeconds(5))

        } catch (e: Exception) {
            logger.warn("[EXTERNAL_DOMAIN_ERROR] auth | Error in member domain service: ${e.message}")
            null
        }


}