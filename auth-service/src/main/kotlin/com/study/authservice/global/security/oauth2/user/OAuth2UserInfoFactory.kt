package com.study.authservice.global.security.oauth2.user

import com.study.authservice.entity.AuthProviderType
import com.study.authservice.global.exception.OAuth2AuthenticationProcessingException

class OAuth2UserInfoFactory {

    companion object {
        fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return if (registrationId.equals(AuthProviderType.GOOGLE.toString(), ignoreCase = true)) {
                GoogleOAuth2UserInfo(attributes)
            } else if (registrationId.equals(AuthProviderType.FACEBOOK.toString(), ignoreCase = true)) {
                FacebookOAuth2UserInfo(attributes)
            } else if (registrationId.equals(AuthProviderType.GITHUB.toString(), ignoreCase = true)) {
                GithubOAuth2UserInfo(attributes)
            } else {
                throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
            }
        }
    }
}