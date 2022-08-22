package com.study.authservice.api.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.study.authservice.entity.vo.HomeAddress
import com.study.authservice.service.dto.UserDto
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserSignUpRequest(
    @JsonProperty(required = true)
    @field:NotEmpty(message = "email은 필수값입니다.")
    @field:Email(message = "email 형식에 맞지않습니다.")
    var email: String,

    @JsonProperty(required = true)
    @field:NotEmpty(message = "password는 필수값입니다.")
    var password: String,

    @JsonProperty(required = true)
    @field:NotEmpty(message = "username은 필수값입니다.")
    var username: String,
) {
    fun toUserDto(): UserDto {
        return UserDto(
            email = this.email,
            password = this.password,
            name = this.username,
        )
    }
}

