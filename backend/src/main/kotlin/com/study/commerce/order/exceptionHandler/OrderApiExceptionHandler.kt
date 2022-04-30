package com.study.commerce.order.exceptionHandler

import com.study.commerce.order.exception.OrderNotFoundException
import com.study.commerce.shared.response.CommonApiErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice(basePackages = ["com.study.commerce.order.api"])
class OrderApiExceptionHandler {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleNotFoundOrderException(exception: OrderNotFoundException, request: WebRequest): CommonApiErrorResponse {
        log.error("Not found Order.", exception);
        return CommonApiErrorResponse(request.getDescription(false), HttpStatus.NOT_FOUND, exception.message)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleNotRunException(exception: RuntimeException, request: WebRequest): CommonApiErrorResponse {
        log.error("Fail to response order api.", exception);
        return CommonApiErrorResponse(request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, exception.message!!)
    }
}