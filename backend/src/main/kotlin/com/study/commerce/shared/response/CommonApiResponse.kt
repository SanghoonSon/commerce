package com.study.commerce.shared.response

import org.springframework.http.HttpStatus

data class CommonApiResponse<T>(val data: T, val code: Int, val message:String? = "success") {
    constructor(data: T, httpStatus: HttpStatus, message:String? = ""): this(data, httpStatus.value(), message)
}