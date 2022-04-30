package com.study.commerce.order.service.dto

import com.study.commerce.order.domain.entity.OrderProduct

data class OrderProductDto(
    var productId: Long,
    var price: Int,
    var quantity: Int,
    var totalPrice: Int
) {
    companion object {
        fun fromEntity(orderProduct: OrderProduct) = OrderProductDto(
            productId = orderProduct.product.productId!!,
            price = orderProduct.price,
            quantity = orderProduct.quantity,
            totalPrice = orderProduct.totalCost.price
        )
    }
}
