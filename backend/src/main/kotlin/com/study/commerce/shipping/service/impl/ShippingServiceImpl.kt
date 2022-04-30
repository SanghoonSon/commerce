package com.study.commerce.shipping.service.impl

import com.study.commerce.order.exception.OrderNotFoundException
import com.study.commerce.order.repository.OrderRepository
import com.study.commerce.shipping.domain.entity.Shipping
import com.study.commerce.shipping.domain.enums.PackingSize
import com.study.commerce.shipping.domain.enums.PackingType
import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.api.request.ShippingCreateRequest
import com.study.commerce.shipping.repository.ShippingRepository
import com.study.commerce.shipping.service.ShippingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ShippingServiceImpl(
    private val shippingRepository: ShippingRepository,
    private val orderRepository: OrderRepository
) : ShippingService {

    @Transactional
    override fun createShipping(shippingCreateRequest: ShippingCreateRequest): Long {

        val order = orderRepository.findById(shippingCreateRequest.orderId)
            .orElseThrow { OrderNotFoundException("Not found order ${shippingCreateRequest.orderId}.") }

        val shipping = Shipping(
            order = order,
            address = shippingCreateRequest.toAddress(),
            message = shippingCreateRequest.shippingMessage,
            packing = Packing(PackingType.PAPER, PackingSize.MIDDLE)
        )
        return shippingRepository.save(shipping).id!!
    }
}