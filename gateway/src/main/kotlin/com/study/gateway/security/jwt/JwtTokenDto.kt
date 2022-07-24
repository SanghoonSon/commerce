package com.study.gateway.security.jwt

data class JwtTokenDto(
    var email: String,
    var role: List<String>,
    var isExpired: Boolean
)
