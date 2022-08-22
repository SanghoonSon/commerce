package com.study.authservice.repository

import com.study.authservice.entity.OAuthUser
import com.study.authservice.entity.vo.AuthProvider
import org.springframework.data.jpa.repository.JpaRepository

interface OAuthUserRepository: JpaRepository<OAuthUser, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmailAndAuthProvider(email: String, authProvider: AuthProvider): OAuthUser?
}