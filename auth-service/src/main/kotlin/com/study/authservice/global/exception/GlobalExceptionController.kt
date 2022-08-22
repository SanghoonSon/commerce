package com.study.authservice.global.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.study.authservice.exception.UserAlreadyCreatedException
import com.study.authservice.global.ApiResponse
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalExceptionController {
    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(UserAlreadyCreatedException::class)
    fun handleUserAlreadyCreatedException(e: UserAlreadyCreatedException): ResponseEntity<Any> {
        logger.error(e) { e.message }
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindException(e: MethodArgumentNotValidException): ResponseEntity<Any> {
        logger.error(e) { "Fail to bind arguments." }
        return ResponseEntity.badRequest().body(e.bindingResult.allErrors.map { it.defaultMessage })
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleServerWebInputException(e: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Any>> {
        if(e.rootCause is MissingKotlinParameterException) {
            return handleMissingKotlinParameterException(e.rootCause as MissingKotlinParameterException)
        }
        logger.error(e) { "Fail to read request message." }
        return ResponseEntity.badRequest().body(ApiResponse.badRequest("Fail to read request message"))
    }

    private fun handleMissingKotlinParameterException(e: MissingKotlinParameterException): ResponseEntity<ApiResponse<Any>> {
        logger.error(e) { "Missing required parameters." }

        val parameterName = e.parameter.name // id
        val parameterType = e.parameter.type // object id
        val fieldName = e.path[0].fieldName // in User part
        val errorResponse = ApiResponse.badRequest<Any>(
            "there is a missing parameter in your request, check your request body." +
                    " detail : missing $parameterName ($parameterType) type in $fieldName"
        )
        return ResponseEntity.badRequest().body(errorResponse)
    }
}