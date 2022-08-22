package com.study.authservice.global.security

import com.study.authservice.service.UserQueryService
import com.study.authservice.service.dto.UserDto
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(
    private val userQueryService: UserQueryService
): UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userQueryService.findByEmail(email)
        return createUserPrincipalByUser(user);
    }

    fun loadUserById(userId: Long): UserDetails {
        val user = userQueryService.findById(userId)
        return createUserPrincipalByUser(user);
    }

    fun createUserPrincipalByUser(userDto: UserDto): UserPrincipal {
        return UserPrincipal.create(
            id= userDto.id!!,
            email = userDto.email,
            username = userDto.name,
            password = userDto.password)
    }
}