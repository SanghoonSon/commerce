package com.study.authservice.service.dto

import com.study.authservice.entity.vo.HomeAddress
import com.study.authservice.entity.User
import com.study.authservice.entity.UserStatus
import java.time.LocalDateTime

data class UserDto(
    var id: String? = null,
    var email: String,
    var name: String,
    var password: String,
    var phoneNumber: String? = null,
    var homeAddress: HomeAddress? = null,
    var status: UserStatus? = null,
    var certificatedDate: LocalDateTime? = null
) {

    companion object {
        fun fromEntity(user: User): UserDto {
            return UserDto(
                id = user.id,
                email = user.email,
                name = user.name,
                password = user.password,
                phoneNumber = user.phoneNumber,
                homeAddress = user.homeAddress,
                status = user.status,
                certificatedDate = user.certificatedDate
            )
        }
    }

    fun toEntity(): User {
        return User(
            id = this.id,
            email = this.email,
            name = this.name,
            password = this.password,
            phoneNumber = this.phoneNumber,
            homeAddress = this.homeAddress,
            status = this.status,
            certificatedDate = this.certificatedDate,
        )
    }
}
