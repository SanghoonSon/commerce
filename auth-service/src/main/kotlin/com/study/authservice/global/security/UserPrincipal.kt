package com.study.authservice.global.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

open class UserPrincipal(
    val id: String,
    val email: String,
    private val username: String,
    private val password: String? = null,
    private val authorities: MutableCollection<out GrantedAuthority>,
): UserDetails {

    companion object {
        fun create(id: String, email: String, username: String, password: String): UserPrincipal {
            val authorities = mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_USER"))

            return UserPrincipal(id, email, username, password, authorities)
        }
    }

    override fun getAuthorities() = this.authorities

    override fun getPassword() = this.password

    override fun getUsername() = this.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    fun getUserEmail() = this.email
}