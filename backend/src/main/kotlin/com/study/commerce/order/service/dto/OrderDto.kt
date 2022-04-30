package com.study.commerce.order.service.dto

import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.enums.OrderStatus
import com.study.commerce.shared.domain.vo.Money
import com.study.commerce.shipping.service.dto.ShippingDto

data class OrderDto(
    var id: Long,
    var orderStatus: OrderStatus,
    var totalCost: Money,
    var userId : Long,
    var orderProductDto: MutableList<OrderProductDto>,
    var shippingDto: ShippingDto
) {
    companion object {
        fun fromEntity(order: Order) = OrderDto(
            id = order.id!!,
            orderStatus = order.orderStatus,
            totalCost = order.getTotalCost(),
            userId = order.userId,
            orderProductDto = order.orderProducts.map { OrderProductDto.fromEntity(it) }.toMutableList(),
            shippingDto = ShippingDto.fromEntity(order.shipping!!)
        )
    }
}
