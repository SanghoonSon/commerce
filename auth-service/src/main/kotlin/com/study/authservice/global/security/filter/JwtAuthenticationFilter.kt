package com.study.authservice.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.authservice.api.request.UserSignInRequest
import com.study.authservice.global.security.SecurityConst
import com.study.authservice.global.security.UserPrincipal
import com.study.authservice.global.util.HttpHeaderUtils
import com.study.authservice.service.cache.JwtTokenService
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.nio.charset.Charset
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//https://velog.io/@bum12ark/MSA-JWT-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0-1.-%EB%A1%9C%EA%B7%B8%EC%9D%B8
class JwtAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper,
    private val jwtTokenService: JwtTokenService
): UsernamePasswordAuthenticationFilter() {
    private val authManager: AuthenticationManager
    private val log = LoggerFactory.getLogger(this::class.java)

    init {
        setFilterProcessesUrl(SecurityConst.AUTH_LOGIN_URL)
        authManager = authenticationManager
    }

    // login 리퀘스트 패스로 오는 요청을 판단
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val credential: UserSignInRequest?
        try {
            // POST 요청으로 들어오는 email과 password
            credential = objectMapper.readValue(request.inputStream, UserSignInRequest::class.java)
        } catch (e: IOException) {
            logger.error("Fail to read sign in request - ${e.message}")
            throw RuntimeException(e);
        }

        if(log.isDebugEnabled) {
            log.debug("Authenticate '${credential.email}' user");
        }

        // UsernamePasswordAuthenticationToken을 통해
        // loadUserByUsername 메소드에서 로그인 판별
        val usernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(credential.email, credential.password)
        return this.authManager.authenticate(usernamePasswordAuthenticationToken)
    }

    // login 후 토큰 생성
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        // response header에 넣어줄 token 및 expired time 생성
        val token = jwtTokenService.createJwtTokens(authResult)
        HttpHeaderUtils.appendTokenHeader(response, token)
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val userPrincipal = authResult.principal as UserPrincipal
        val userBasics: MutableMap<String, String> = mutableMapOf()
        userBasics["email"] = userPrincipal.email
        userBasics["username"] = userPrincipal.username
        objectMapper.writeValue(response.writer, userBasics)
    }
}