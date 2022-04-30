package com.study.commerce.order.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
data class OrderNotFoundException(override val message: String): RuntimeException(message)