package com.study.commerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class CommerceApplication

fun main(args: Array<String>) {
    runApplication<CommerceApplication>(*args)
}
