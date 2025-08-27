package org.jeongmo.practice.global.security.auth.service

import org.jeongmo.practice.global.security.auth.converter.AuthConverter.toEntity
import org.jeongmo.practice.global.security.auth.dto.AuthRequestDTO
import org.jeongmo.practice.domain.member.entity.Member
import org.jeongmo.practice.domain.member.repository.MemberRepository
import org.jeongmo.practice.global.error.code.TokenErrorCode
import org.jeongmo.practice.global.error.exception.TokenException
import org.jeongmo.practice.global.security.auth.dto.AuthResponseDTO
import org.jeongmo.practice.global.security.token.service.TokenService
import org.jeongmo.practice.global.security.token.service.TokenStorageService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
    private val tokenStorageService: TokenStorageService,
    private val userDetailsService: UserDetailsService,
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
): AuthService {

    override fun signUp(request: AuthRequestDTO.SignUp): Member {
        return memberRepository.save(request.toEntity(passwordEncoder))
    }

    override fun reissueToken(request: AuthRequestDTO.ReissueToken): AuthResponseDTO.ReissueToken {
        if (tokenStorageService.isBlackList(request.refreshToken)) {
            throw TokenException(TokenErrorCode.INVALID_TOKEN)
        }

        val userDetails: UserDetails = userDetailsService.loadUserByUsername(tokenService.getSubject(request.refreshToken))
        val accessToken: String = tokenService.createAccessToken(userDetails)

        return AuthResponseDTO.ReissueToken(accessToken)
    }

}