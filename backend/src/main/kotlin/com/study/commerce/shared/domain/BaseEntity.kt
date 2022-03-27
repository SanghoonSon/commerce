package com.study.commerce.shared.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {

    @CreatedDate
    lateinit var createdDate: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedDate: LocalDateTime
}