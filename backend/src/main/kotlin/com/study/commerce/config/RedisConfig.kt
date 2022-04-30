package com.study.commerce.config

import org.springframework.cache.annotation.CachingConfigurerSupport

//@Configuration
//@EnableRedisRepositories
//@EnableCaching
class RedisConfig : CachingConfigurerSupport(){

    /*@Value("\${spring.redis.host}")
    private val redisHost: String? = null

    @Value("\${spring.redis.port}")
    private val redisPort: Int = 0

    @Value("\${spring.redis.password}")
    private val redisPassword: String? = null

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = redisHost!!
        redisStandaloneConfiguration.port = redisPort
        redisStandaloneConfiguration.password = RedisPassword.of(redisPassword)

        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = GenericJackson2JsonRedisSerializer();

        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.setEnableTransactionSupport(true)

        return redisTemplate
    }*/

}