package com.study.authservice.service.cache

import com.study.authservice.entity.cache.RefreshToken
import com.study.authservice.global.exception.NotValidRefreshTokenException
import com.study.authservice.global.exception.RefreshTokenNotFoundException
import com.study.authservice.exception.UserNotFoundException
import com.study.authservice.global.security.UserPrincipal
import com.study.authservice.global.security.jwt.JwtTokenDto
import com.study.authservice.global.security.jwt.TokenProvider
import com.study.authservice.repository.cache.RefreshTokenRedisRepository
import com.study.authservice.service.UserQueryService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JwtTokenService(
    private val tokenProvider: TokenProvider,
    private val userQueryService: UserQueryService,
    private val refreshTokenRedisRepository: RefreshTokenRedisRepository
) {

    @Transactional
    fun updateRefreshToken(email: String, uuid: String) {
        refreshTokenRedisRepository.save(RefreshToken(email, uuid))
    }

    fun getRefreshToken(email: String): RefreshToken {
        return refreshTokenRedisRepository.findById(email)
            .orElseThrow { RefreshTokenNotFoundException("${email}로 등록 된 Refresh Token이 없습니다.") }
    }

    fun changeRefreshToken(accessToken: String, refreshToken: String): JwtTokenDto {
        val userEmail = tokenProvider.getUserIdFromToken(accessToken)
        if(!userQueryService.isExistsByEmail(userEmail)) {
            throw UserNotFoundException("${userEmail}로 등록 된 계정이 없습니다.")
        }

        val storedRefreshToken = getRefreshToken(userEmail)

        if(storedRefreshToken.refreshToken != refreshToken) {
            throw NotValidRefreshTokenException("${userEmail}로 저장 된 Refresh Token와 일치하지 않습니다.")
        }

        val newRefreshToken = tokenProvider.createRefreshToken(userEmail)
        updateRefreshToken(userEmail, newRefreshToken)

        return JwtTokenDto(
            userId = userEmail,
            accessToken = refreshToken,
            accessTokenExpiredTime = tokenProvider.createAccessTokenExpiredTime(),
            accessTokenTokenValidityTime = tokenProvider.getAccessTokenExpirationSeconds(),
            refreshToken = newRefreshToken,
            refreshTokenExpiredTime = tokenProvider.getExpiredTime(newRefreshToken),
            refreshTokenValidityTime = tokenProvider.getRefreshTokenExpirationSeconds()
        )
    }

    fun createJwtTokens(authentication: Authentication): JwtTokenDto {
        val principal = authentication.principal as UserPrincipal
        val userEmail = principal.email

        val accessToken = tokenProvider.createToken(authentication)
        val refreshToken = tokenProvider.createRefreshToken(userEmail)

        updateRefreshToken(userEmail, refreshToken)
        return JwtTokenDto(
            userId = userEmail,
            accessToken = accessToken,
            accessTokenExpiredTime = tokenProvider.getExpiredTime(accessToken),
            accessTokenTokenValidityTime = tokenProvider.getAccessTokenExpirationSeconds(),
            refreshToken = refreshToken,
            refreshTokenExpiredTime = tokenProvider.getExpiredTime(refreshToken),
            refreshTokenValidityTime = tokenProvider.getRefreshTokenExpirationSeconds()
        )
    }
}