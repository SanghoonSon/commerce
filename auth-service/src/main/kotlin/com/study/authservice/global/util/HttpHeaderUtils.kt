package com.study.authservice.global.util

import com.study.authservice.global.security.SecurityConst
import com.study.authservice.global.security.jwt.JwtTokenDto
import org.springframework.http.HttpHeaders
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletResponse

abstract class HttpHeaderUtils private constructor() {

    companion object {
        fun tokenHeader(token: JwtTokenDto): MutableMap<String, String> {
            val headers = mutableMapOf<String, String>()
            headers[SecurityConst.TOKEN_HEADER] = token.accessToken
            headers[SecurityConst.TOKEN_EXPIRED_TIME] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(token.accessTokenExpiredTime)
            headers[SecurityConst.REFRESH_TOKEN_HEADER] = token.refreshToken
            return headers
        }

        fun appendTokenHeader(response: HttpServletResponse, token: JwtTokenDto) {
            response.addHeader(SecurityConst.TOKEN_HEADER, token.accessToken)
            response.addHeader(SecurityConst.REFRESH_TOKEN_HEADER, token.refreshToken)
            response.addHeader(SecurityConst.TOKEN_EXPIRED_TIME, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(token.accessTokenExpiredTime))
        }

        fun appendHeader(response: HttpServletResponse, key: String, value: String) {
            response.addHeader(key, value)
        }
    }
}