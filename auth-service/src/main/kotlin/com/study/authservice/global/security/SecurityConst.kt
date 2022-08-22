package com.study.authservice.global.security

object SecurityConst {
    const val AUTH_LOGIN_URL = "/api/v1/auth/signin"

    // JWT token defaults
    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_EXPIRED_TIME = "ExpiredTime"
    const val REFRESH_TOKEN_HEADER = "RefreshToken"
}