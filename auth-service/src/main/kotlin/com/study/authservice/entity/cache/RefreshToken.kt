package com.study.authservice.entity.cache

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.Date

@RedisHash("refresh_token")
data class RefreshToken(
    @Id
    val userId: String,
    var refreshToken: String
)
