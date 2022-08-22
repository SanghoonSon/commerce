package com.study.authservice.entity.vo

import com.study.authservice.entity.AuthProviderType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class AuthProvider(
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    val authProviderType: AuthProviderType,

    @Column(length = 30)
    val authProviderId: String,
)
