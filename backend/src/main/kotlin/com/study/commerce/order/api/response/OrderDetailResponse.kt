package com.study.commerce.order.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.study.commerce.order.domain.enums.OrderStatus
import com.study.commerce.order.service.dto.OrderDto
import com.study.commerce.order.service.dto.OrderProductDto
import com.study.commerce.shared.domain.vo.Money
import com.study.commerce.shipping.service.dto.ShippingDto

data class OrderDetailResponse (
    @JsonProperty("order-id", required = true)
    var id: Long,

    @JsonProperty(value = "order-status", required = true)
    var orderStatus: OrderStatus,

    @JsonProperty(value = "total-cost", required = true)
    var totalCost: Money,

    @JsonProperty("user-id")
    var userId : Long,

    @JsonProperty("order-product")
    var orderProductDto: MutableList<OrderProductDto>,

    @JsonProperty("shipping")
    var shippingDto: ShippingDto
) {
    companion object {
        fun fromDto(orderDto: OrderDto): OrderDetailResponse = OrderDetailResponse(
            id = orderDto.id,
            orderStatus = orderDto.orderStatus,
            totalCost = orderDto.totalCost,
            userId = orderDto.userId,
            orderProductDto = orderDto.orderProductDto,
            shippingDto = orderDto.shippingDto
        )
    }
}
