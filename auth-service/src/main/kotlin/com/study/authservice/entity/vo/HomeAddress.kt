package com.study.authservice.entity.vo

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class HomeAddress(
    @Column(length = 10)
    val zipcode: String,

    @Column(length = 100)
    val address: String,

    @Column(length = 200)
    val addressDetails: String,
)
