package com.study.commerce.order.dto

import com.study.commerce.order.domain.entity.Shipping
import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.entity.OrderProduct
import com.study.commerce.order.domain.vo.Address
import com.study.commerce.product.domain.Product
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

    var orderMessage: String = "",

    var orderProducts: MutableList<OrderProductDto>
) {
    fun toShipping(): Shipping {
        return Shipping(Address(zipcode, address, addressDetails), orderMessage)
    }
}