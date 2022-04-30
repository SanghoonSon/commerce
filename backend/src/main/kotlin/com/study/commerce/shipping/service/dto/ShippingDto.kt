package com.study.commerce.shipping.service.dto

import com.study.commerce.shipping.domain.entity.Shipping
import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.domain.vo.ShippingAddress

data class ShippingDto(
    var address: ShippingAddress,
    var message: String,
    var packing: Packing
) {
    companion object {
        fun fromEntity(shipping: Shipping) = ShippingDto(
            address = shipping.address,
            message = shipping.message,
            packing = shipping.packing
        )
    }
}
