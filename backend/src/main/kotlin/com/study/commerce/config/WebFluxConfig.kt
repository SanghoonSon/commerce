package com.study.commerce.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebFluxConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) // 3초
                        .doOnConnected { conn ->
                            conn.addHandlerLast(ReadTimeoutHandler(10))
                        }))
            .codecs { it.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
            .build()
    }
}