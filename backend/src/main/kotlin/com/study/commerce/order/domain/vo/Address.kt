package com.study.commerce.order.domain.vo

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Address(
    @Column
    val zipcode: String,
    @Column
    val address: String,
    @Column
    val addressDetails: String
): java.io.Serializable
