package com.study.authservice.api.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserSignInRequest(
    @JsonProperty(required = true)
    @field:NotEmpty(message = "email은 필수값입니다.")
    @field:Email(message = "email 형식에 맞지않습니다.")
    val email: String,

    @JsonProperty(required = true)
    @field:NotEmpty(message = "password는 필수값입니다.")
    val password: String
)
