package com.study.commerce.order.service

import com.study.commerce.order.domain.entity.Shipping
import com.study.commerce.order.dto.OrderProductDto
import com.study.commerce.order.dto.OrderSearchResponse

interface OrderService {
    fun createOrder(userId:Long, shipping: Shipping, vararg orderProducts: OrderProductDto): Long

    fun findOrderById(id: Long): OrderSearchResponse
}