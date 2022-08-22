package com.study.authservice.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "token")
data class TokenProperty(
    val accessExpiredTime: Long? = null,
    val refreshExpiredTime: Long? = null,
    val secret: String? = null
)
