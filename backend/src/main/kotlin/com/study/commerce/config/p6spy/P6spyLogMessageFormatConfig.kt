package com.study.commerce.config.p6spy

import com.p6spy.engine.spy.P6SpyOptions
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class P6spyLogMessageFormatConfig {

    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spySqlFormatConfig::class.java.name
    }
}