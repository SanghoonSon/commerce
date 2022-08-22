package com.study.authservice.global.security.jwt

import java.util.*

data class JwtTokenDto(
    val userId: String,
    val accessToken: String,
    val accessTokenExpiredTime: Date,
    val accessTokenTokenValidityTime: Long,
    val refreshToken: String,
    val refreshTokenExpiredTime: Date,
    val refreshTokenValidityTime: Long
)
