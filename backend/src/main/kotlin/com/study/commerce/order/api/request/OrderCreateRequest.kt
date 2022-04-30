package com.study.commerce.order.api.request

import com.study.commerce.order.service.dto.OrderProductDto
import com.study.commerce.shipping.domain.vo.ShippingAddress
import javax.validation.constraints.NotBlank

data class OrderCreateRequest(
    @field: NotBlank
    var userId: Long,

    @field: NotBlank
    var zipcode: String,

    @field: NotBlank
    var address: String,

    @field: NotBlank
    var addressDetails: String,

    @field: NotBlank
    var placeToReceive: String,

    var shippingMessage: String = "",

    var orderProducts: MutableList<OrderProductDto>
) {
    fun toShippingAddress(): ShippingAddress {
        return ShippingAddress(zipcode, address, addressDetails, placeToReceive)
    }
}