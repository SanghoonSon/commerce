package com.study.commerce.shared.response

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CommonApiErrorResponse(val instance: String, var status: String?, var code: Int?, val errorMessage:String) {
    constructor(instance: String, status: HttpStatus, errorMessage:String) : this(instance, status.reasonPhrase, status.value(), errorMessage)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
}