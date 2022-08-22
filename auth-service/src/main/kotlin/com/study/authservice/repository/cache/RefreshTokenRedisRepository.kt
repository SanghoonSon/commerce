package com.study.authservice.repository.cache

import com.study.authservice.entity.cache.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRedisRepository: CrudRepository<RefreshToken, String> {
    fun findByUserId(userId: String): RefreshToken?
}
