package com.study.commerce.shared.domain.vo

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
abstract class Address(
    @Column
    open val zipcode: String,

    @Column
    open val address: String,

    @Column
    open val addressDetails: String
): java.io.Serializable
