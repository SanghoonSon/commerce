package com.study.authservice.api

import com.study.authservice.api.request.UserSignUpRequest
import com.study.authservice.global.ApiResponse
import com.study.authservice.global.security.SecurityConst
import com.study.authservice.global.util.HttpHeaderUtils
import com.study.authservice.service.UserCreateService
import com.study.authservice.service.cache.JwtTokenService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
class AuthApiControllerV1(
    private val userCreateService: UserCreateService,
    private val jwtTokenService: JwtTokenService
) {

    @PostMapping(value= ["/signup"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signUpRequest(@Valid @RequestBody signUpRequest: UserSignUpRequest): ApiResponse<Any> {
        userCreateService.signUp(signUpRequest.toUserDto())
        return ApiResponse.success()
    }

    @GetMapping("/reissue", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun refreshToken(@RequestHeader(SecurityConst.TOKEN_HEADER) accessToken: String,
                     @RequestHeader(SecurityConst.REFRESH_TOKEN_HEADER) refreshToken: String,
    ): ApiResponse<Any> {
        val changedToken = jwtTokenService.changeRefreshToken(accessToken, refreshToken)
        val tokenHeader = HttpHeaderUtils.tokenHeader(changedToken)
        return ApiResponse.successWithHeaders(tokenHeader)
    }
}