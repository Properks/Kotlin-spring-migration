package org.jeongmo.practice.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.jeongmo.practice.global.security.filter.dto.LoginResponseDTO
import org.jeongmo.practice.global.security.token.service.TokenService
import org.jeongmo.practice.global.security.token.service.TokenStorageService
import org.jeongmo.practice.global.util.HttpResponseWriter
import org.namul.api.payload.code.DefaultResponseSuccessCode
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomAuthenticationSuccessHandler(
    private val tokenService: TokenService<CustomUserDetails>,
    private val tokenStorageService: TokenStorageService,
    private val httpResponseWriter: HttpResponseWriter<DefaultResponseSuccessReasonDTO, DefaultResponseErrorReasonDTO>,
): AuthenticationSuccessHandler{
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val userDetails: CustomUserDetails = CustomUserDetails::class.java.cast(authentication?.principal)
        val accessToken: String = tokenService.createAccessToken(userDetails)
        val refreshToken: String = tokenService.createRefreshToken(userDetails)
        tokenStorageService.saveRefreshToken(userDetails.username, refreshToken)

        val successReasonDTO: DefaultResponseSuccessReasonDTO = DefaultResponseSuccessCode._OK.reason
        httpResponseWriter.writeSuccessResponse(
            response!!,
            successReasonDTO.httpStatus,
            successReasonDTO,
            LoginResponseDTO(accessToken, refreshToken, userDetails.authorities.toString(), LocalDateTime.now())
        )
    }
}