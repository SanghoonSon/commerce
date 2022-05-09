package com.study.commerce.shared.response

import org.springframework.http.HttpStatus

data class CommonApiResponse<T>(val data: T? = null, val code: Int? = 200, val message:String? = "success") {
    constructor(httpStatus: HttpStatus, message:String? = ""): this(null, httpStatus.value(), message)
    constructor(data: T, httpStatus: HttpStatus, message:String? = ""): this(data, httpStatus.value(), message)
}