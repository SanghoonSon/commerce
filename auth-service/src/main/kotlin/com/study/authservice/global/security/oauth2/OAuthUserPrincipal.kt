package com.study.authservice.global.security.oauth2

import com.study.authservice.entity.OAuthUser
import com.study.authservice.global.security.UserPrincipal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuthUserPrincipal(
    id: String,
    email: String,
    username: String,
    authorities: MutableCollection<out GrantedAuthority>,
    private var attributes: MutableMap<String, Any>? = null
): UserPrincipal(id, email, username, "", authorities), OAuth2User {

    companion object {
        fun create(oAuthUser: OAuthUser, attributes: MutableMap<String, Any>? = null): OAuthUserPrincipal {
            val authorities = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))

            return OAuthUserPrincipal(
                oAuthUser.id!!,
                oAuthUser.email,
                oAuthUser.name!!,
                authorities,
                attributes
            )
        }
    }

    override fun getName() = this.email

    override fun getAttributes() = this.attributes
}