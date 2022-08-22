package com.study.authservice.global.security.oauth2.user

class GithubOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {

    override fun getId(): String {
        return attributes["id"].toString()
    }

    override fun getName(): String {
        val name = attributes["name"] ?: ""
        return name as String
    }

    override fun getEmail(): String {
        return attributes["email"] as String
    }

    override fun getImageUrl(): String {
        return attributes["avatar_url"] as String
    }
}