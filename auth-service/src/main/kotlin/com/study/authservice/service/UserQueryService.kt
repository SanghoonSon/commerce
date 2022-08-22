package com.study.authservice.service

import com.study.authservice.entity.User
import com.study.authservice.exception.UserNotFoundException
import com.study.authservice.repository.UserRepository
import com.study.authservice.service.dto.UserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryService(
    private val userRepository: UserRepository
) {

    fun findByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException("{$email}로 등록 된 유저가 없습니다.") }
        return UserDto.fromEntity(user)
    }

    fun isExistsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun findById(userId: Long): UserDto {
        val user = userRepository.findById(userId)
            .orElseThrow { throw UserNotFoundException("{$userId}로 등록 된 유저가 없습니다.") }
        return UserDto.fromEntity(user)
    }
}