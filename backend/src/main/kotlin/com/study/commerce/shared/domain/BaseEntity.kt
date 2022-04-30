package com.study.commerce.shared.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false)
    var createdDate: LocalDateTime? = null
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var modifiedDate: LocalDateTime? = null
        protected set
}