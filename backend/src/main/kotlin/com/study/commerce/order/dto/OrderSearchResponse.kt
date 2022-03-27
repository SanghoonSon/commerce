package com.study.commerce.order.dto

import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.vo.Address

data class OrderSearchResponse (
    var id: Long,
    var deliveryAddress: Address,
    var deliveryMessage: String
) {
    companion object {
        fun from(order: Order): OrderSearchResponse {
            return OrderSearchResponse(order.id!!, order.shipping.address, order.shipping.message)
        }
    }
}
