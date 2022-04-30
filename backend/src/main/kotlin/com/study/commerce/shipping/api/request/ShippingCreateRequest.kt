package com.study.commerce.shipping.api.request

import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.domain.vo.ShippingAddress
import javax.validation.constraints.NotBlank

data class ShippingCreateRequest(
    @field: NotBlank
    var orderId: Long,

    @field: NotBlank
    var zipcode: String,

    @field: NotBlank
    var address: String,

    @field: NotBlank
    var addressDetails: String,

    @field: NotBlank
    var placeToReceive: String,

    var shippingMessage: String = "",

    var packing: Packing,
) {
    fun toAddress(): ShippingAddress {
        return ShippingAddress(zipcode, address, addressDetails, placeToReceive)
    }
}