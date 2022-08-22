package com.study.authservice

import com.study.authservice.property.AppProperties
import com.study.authservice.property.TokenProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableConfigurationProperties(TokenProperty::class, AppProperties::class)
@EnableEurekaClient
@EnableJpaAuditing
@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
