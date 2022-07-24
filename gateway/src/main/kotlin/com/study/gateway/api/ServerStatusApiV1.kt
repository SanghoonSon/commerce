package com.study.gateway.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/status")
class ServerStatusApiV1 {

    @GetMapping
    fun getHealthCheckResult(): String {
        return "Sever is alive!"
    }
}