package com.study.commerce.order.service.impl

import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.entity.OrderProduct
import com.study.commerce.order.domain.entity.Shipping
import com.study.commerce.order.dto.OrderProductDto
import com.study.commerce.order.dto.OrderSearchResponse
import com.study.commerce.order.repository.OrderRepository
import com.study.commerce.order.service.OrderService
import com.study.commerce.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) : OrderService {

    @Transactional
    override fun createOrder(userId: Long, shipping: Shipping, vararg orderProducts: OrderProductDto): Long {

        val createdOrderProducts = productRepository.findAllById(orderProducts.map { it.productId })
            .map {
                val orderProduct = orderProducts.first { orderProduct -> orderProduct.productId == it.productId }
                OrderProduct(it, orderProduct.quantity, orderProduct.price)
            }

        val order = Order(shipping, *createdOrderProducts.toTypedArray())
        return orderRepository.save(order).id!!
    }

    override fun findOrderById(id: Long): OrderSearchResponse {
        val order = orderRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("Not Found $id Order.") }
        return OrderSearchResponse.from(order)
    }
}