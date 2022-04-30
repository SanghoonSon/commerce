package com.study.commerce.shipping.domain.vo

import com.study.commerce.shared.domain.vo.Address
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class ShippingAddress(
    override val zipcode: String,
    override val address: String,
    override val addressDetails: String,

    @Column
    val placeToReceive: String
): Address(zipcode, address, addressDetails)
