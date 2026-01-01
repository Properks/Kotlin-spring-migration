package org.jeongmo.migration.auth.infrastructure.adapter.out.member.api

import org.jeongmo.migration.auth.application.error.code.AuthErrorCode
import org.jeongmo.migration.auth.application.error.exception.AuthException
import org.jeongmo.migration.auth.application.port.out.member.MemberServiceClient
import org.jeongmo.migration.auth.application.port.out.member.dto.*
import org.namul.api.payload.response.supports.DefaultResponse
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

        return try {
            sendRequest("/internal/api/members", request, type)?.result ?: throw AuthException(AuthErrorCode.FAIL_SIGN_UP)
        } catch (e: AuthException) {
            logger.warn("[FAIL_API] auth-service | Cannot get response from member domain (sign-up request)")
            throw e
        } catch (e: Exception) {
            logger.warn("[FAIL_API] auth-service | Fail api call to member service (sign-up request)")
            throw AuthException(AuthErrorCode.FAIL_SIGN_UP, e)
        }
    }

    override fun verifyMember(request: VerifyMemberRequest): VerifyMemberResponse {
        val type = object: ParameterizedTypeReference<DefaultResponse<VerifyMemberResponse?>>() {}

        return try {
            sendRequest("/internal/api/members/verify", request, type)?.result ?: throw AuthException(AuthErrorCode.FAIL_TO_VERIFY)
        } catch (e: AuthException) {
            logger.warn("[FAIL_API] auth-service | Cannot get response from member domain (login request)")
            throw e
        } catch (e: Exception) {
            logger.warn("[FAIL_API] auth-service | Fail api call to member service (login request)")
            throw AuthException(AuthErrorCode.FAIL_TO_VERIFY, e)
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
            logger.warn("[EXTERNAL_DOMAIN_ERROR] auth-service | Error in member domain service")
            logger.debug("Exception Details: ", e)
            throw e
        }


}