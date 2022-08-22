package com.study.authservice.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.study.authservice.api.request.UserSignUpRequest
import com.study.authservice.service.UserCreateService
import com.study.authservice.service.cache.JwtTokenService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthApiControllerV1::class)
@AutoConfigureMockMvc(addFilters = false)
@MockkBean(JpaMetamodelMappingContext::class)
internal class AuthApiControllerV1Test(@Autowired val mockMvc: MockMvc) {

    @MockkBean lateinit var userCreateService: UserCreateService
    @MockkBean lateinit var jwtTokenService: JwtTokenService
    private val mapper = jacksonObjectMapper()

    @Test
    @DisplayName("신규유저 회원가입(POST)")
    fun signUpRequest_unit_test() {
        // given
        val userSignupRequest = UserSignUpRequest(
            email = "test@email.com",
            password = "password1",
            username = "testUser"
        )
        every { userCreateService.signUp(userSignupRequest.toUserDto()) } just Runs

        // when && then
        mockMvc.perform(post("/api/v1/auth/signup")
            .content(mapper.writeValueAsString(userSignupRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        verify(exactly = 1) { userCreateService.signUp(userSignupRequest.toUserDto()) }
    }

    @Test
    @DisplayName("access token 재발급(GET)")
    fun refreshToken_unit_test() {
        // given
        every {  }

    }
}