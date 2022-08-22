package com.study.authservice.service

import com.study.authservice.exception.UserAlreadyCreatedException
import com.study.authservice.repository.UserRepository
import com.study.authservice.service.dto.UserDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserCreateService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {

    @Transactional
    fun signUp(userDto: UserDto) {
        if(userRepository.existsByEmail(userDto.email)) {
            throw UserAlreadyCreatedException("${userDto.email}로 생성 된 계정이 있습니다.")
        }

        val userEntity = userDto.toEntity()
        userEntity.password = passwordEncoder.encode(userEntity.password)
        userRepository.save(userEntity)
    }
}