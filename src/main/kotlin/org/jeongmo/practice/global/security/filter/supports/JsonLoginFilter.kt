package org.jeongmo.practice.global.security.filter.supports

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.jeongmo.practice.global.security.filter.LoginFilter
import org.jeongmo.practice.global.security.filter.dto.LoginRequestDTO
import org.jeongmo.practice.global.security.handler.FilterExceptionHandler
import org.namul.api.payload.error.exception.ServerApplicationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.context.SecurityContextRepository

class JsonLoginFilter(
    private val authenticationManager: AuthenticationManager,
    authenticationSuccessHandler: AuthenticationSuccessHandler,
    filterExceptionHandler: FilterExceptionHandler,
    securityContextRepository: SecurityContextRepository
): LoginFilter(securityContextRepository, authenticationSuccessHandler, filterExceptionHandler) {

    private val objectMapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

    override fun attemptAuthentication(request: HttpServletRequest): Authentication {
        try {
            val loginRequestDTO: LoginRequestDTO = getBodyInRequest(request)
            val authentication: Authentication =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.username, loginRequestDTO.password)
            return this.authenticationManager.authenticate(authentication)
        } catch (e: AuthenticationException) {
            throw if (e.cause is ServerApplicationException) e.cause as ServerApplicationException else e
        } catch (e: Exception) {
            throw e
        }
    }

    private fun getBodyInRequest(request: HttpServletRequest): LoginRequestDTO {
        val content: String = HttpServletRequestWrapper(request).inputStream.bufferedReader().use {it.readText()}
        return objectMapper.readValue(content, LoginRequestDTO::class.java)
    }
}