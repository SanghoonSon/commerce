package com.study.authservice.global

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

// https://github.com/prgrms-be-devcourse/BEDV1_Woowahan-Coupons
class ApiResponse<T> private constructor(
    val headers: ApiResponseHeader,
    val body: Map<String, T>? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val serverDateTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private const val SUCCESS = 200
        private const val NOT_FOUND = 404
        private const val BAD_REQUEST = 400
        private const val FAILED = 500
        private const val SUCCESS_MESSAGE = "SUCCESS"
        private const val NOT_FOUND_MESSAGE = "NOT FOUND"
        private const val BAD_REQUEST_MESSAGE = "BAD REQUEST"
        private const val FAILED_MESSAGE = "서버에서 오류가 발생하였습니다."
        private const val INVALID_ACCESS_TOKEN = "Invalid access token."
        private const val INVALID_REFRESH_TOKEN = "Invalid refresh token."
        private const val NOT_EXPIRED_TOKEN_YET = "Not expired token yet."

        fun <T> successWithHeaders(headers: MutableMap<String, String>): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE, headers), null)
        }

        fun <T> success(): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), null)
        }

        fun <T> success(name: String, body: T): ApiResponse<T> {
            val bodyMap: MutableMap<String, T> = mutableMapOf()
            bodyMap[name] = body

            return ApiResponse(ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), bodyMap)
        }

        fun <T> badRequest(message: String = BAD_REQUEST_MESSAGE): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(BAD_REQUEST, message), null)
        }

        fun <T> fail(message: String = FAILED_MESSAGE): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(FAILED, message), null)
        }

        fun <T> invalidAccessToken(): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null)
        }

        fun <T> invalidRefreshToken(): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null)
        }

        fun <T> notExpiredTokenYet(): ApiResponse<T> {
            return ApiResponse(ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null)
        }
    }
}

data class ApiResponseHeader(
    val code: Int,
    var message: String,
    val headers: MutableMap<String, String> = mutableMapOf()
) {
    fun add(headerName: String, headerValue: String) {
        this.headers[headerName] = headerValue
    }
}