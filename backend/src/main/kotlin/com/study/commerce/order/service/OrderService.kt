package com.study.commerce.order.service

import com.study.commerce.order.service.dto.OrderProductDto
import com.study.commerce.order.service.dto.OrderDto
import com.study.commerce.shipping.domain.vo.ShippingAddress

interface OrderService {
    fun createOrder(userId:Long, orderProducts: MutableList<OrderProductDto>): Long

    fun findOrderById(id: Long): OrderDto

    fun requestToCreateShipping(orderId: Long, shippingAddress: ShippingAddress)
}