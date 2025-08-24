package org.jeongmo.practice.global.security.filter.supports

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jeongmo.practice.global.error.code.TokenErrorCode
import org.jeongmo.practice.global.error.exception.TokenException
import org.jeongmo.practice.global.security.domain.CustomUserDetails
import org.jeongmo.practice.global.security.filter.AuthenticationFilter
import org.jeongmo.practice.global.security.token.manager.TokenManager
import org.jeongmo.practice.global.security.token.service.TokenService
import org.jeongmo.practice.global.util.HttpResponseWriter
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.code.dto.supports.DefaultResponseSuccessReasonDTO
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.context.SecurityContextRepository

class TokenAuthenticationFilter(
    private val tokenManager: TokenManager,
    private val tokenService: TokenService<CustomUserDetails>,
    private val userDetailsService: UserDetailsService,
    private val httpResponseWriter: HttpResponseWriter<DefaultResponseSuccessReasonDTO, DefaultResponseErrorReasonDTO>,
    securityContextRepository: SecurityContextRepository,
): AuthenticationFilter(securityContextRepository) {

    override fun getAuthentication(request: HttpServletRequest): Authentication {
        val token: String = tokenManager.getToken(request)
        if (tokenService.isValid(token)) {
            val subject = tokenService.getSubject(token)
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(subject)
            return UsernamePasswordAuthenticationToken.authenticated(userDetails.username, userDetails.password, userDetails.authorities)
        }
        else {
            throw TokenException(TokenErrorCode.INVALID_TOKEN)
        }
    }

    override fun handleServerApplicationException(response: HttpServletResponse, exception: ServerApplicationException) {
        val reasonDTO: ErrorReasonDTO = exception.errorReason
        if (reasonDTO is DefaultResponseErrorReasonDTO) {
            httpResponseWriter.writeSuccessResponse(response, reasonDTO.httpStatus, reasonDTO, null)
        }
        else {
            handleException(response, exception)
        }
    }

    override fun handleException(
        response: HttpServletResponse,
        exception: Exception
    ) {
        val reasonDTO: DefaultResponseErrorReasonDTO = DefaultResponseErrorCode._UNAUTHORIZED.reason
        httpResponseWriter.writeSuccessResponse(
            response,
            reasonDTO.httpStatus,
            reasonDTO,
            result = exception.message
        )
    }

    override fun requireAuthentication(request: HttpServletRequest): Boolean = tokenManager.hasToken(request)
}