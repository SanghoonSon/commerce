package com.study.authservice.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    var auth: Auth,
    var oAuth2: OAuth2
)

data class Auth(
    var tokenSecret: String,
    var tokenExpirationMsec: Long,
    var refreshTokenExpirationMsec: Long
)

data class OAuth2(var authorizedRedirectUris: List<String>)