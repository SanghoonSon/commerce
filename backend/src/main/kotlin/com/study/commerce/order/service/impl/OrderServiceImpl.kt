package com.study.commerce.order.service.impl

import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.entity.OrderProduct
import com.study.commerce.order.exception.OrderNotFoundException
import com.study.commerce.order.repository.OrderRepository
import com.study.commerce.order.service.OrderService
import com.study.commerce.order.service.dto.OrderDto
import com.study.commerce.order.service.dto.OrderProductDto
import com.study.commerce.product.repository.ProductRepository
import com.study.commerce.shared.exception.RequestFailException
import com.study.commerce.shipping.domain.enums.PackingSize
import com.study.commerce.shipping.domain.enums.PackingType
import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.domain.vo.ShippingAddress
import com.study.commerce.shipping.api.request.ShippingCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@Transactional(readOnly = true)
class OrderServiceImpl(
    private val webClient: WebClient,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
) : OrderService {

    @Transactional
    override fun createOrder(userId: Long, orderProducts: MutableList<OrderProductDto>): Long {

        val createdOrderProducts = productRepository.findAllById(orderProducts.map { it.productId })
            .map {
                val orderProduct = orderProducts.first { orderProduct -> orderProduct.productId == it.productId }
                OrderProduct(it, orderProduct.quantity, orderProduct.price)
            }

        if(createdOrderProducts.isEmpty() || createdOrderProducts.size != orderProducts.size) {
           throw IllegalArgumentException("주문한 제품을 조회 할 수 없습니다.")
        }

        val order = Order(userId)
        order.addProducts(*createdOrderProducts.toTypedArray())

        return orderRepository.save(order).id!!
    }

    override fun findOrderById(id: Long): OrderDto {
        val order: Order = orderRepository.findOrderWithAllEntitiesById(id)?: throw OrderNotFoundException("Not Found $id Order.")
        return OrderDto.fromEntity(order)
    }

    override fun requestToCreateShipping(orderId: Long, shippingAddress: ShippingAddress) {
        val shippingCreateRequest = ShippingCreateRequest(
            orderId = orderId,
            zipcode = shippingAddress.zipcode,
            address = shippingAddress.address,
            addressDetails = shippingAddress.addressDetails,
            placeToReceive = shippingAddress.placeToReceive,
            packing = Packing(PackingType.PAPER, PackingSize.MIDDLE)
        )

        webClient.post()
            .uri("/api/v1/shipping")
            .bodyValue(shippingCreateRequest)
            .retrieve()
            .bodyToMono(Long::class.java)
            .onErrorResume { Mono.error(RequestFailException("Fail to create request", it)) }
            .subscribe()
    }
}