package com.study.commerce.order.service

import com.study.commerce.order.dto.OrderCreateRequest
import com.study.commerce.order.dto.OrderProductDto
import com.study.commerce.order.dto.OrderSearchResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class OrderServiceTest {

    @Autowired
    private lateinit var orderService: OrderService

    @Test
    fun `주문_생성`() {
        // given
        val orderCreateRequest: OrderCreateRequest = OrderCreateRequest(
            userId = 1L,
            zipcode = "123-456",
            address = "대전광역시 유성구",
            addressDetails = "예제동 1동 1호",
            orderMessage = "문앞에 놔주세요",
            orderProducts = mutableListOf(
                OrderProductDto(1L, 10000, 2),
                OrderProductDto(1L, 10000, 2),
                OrderProductDto(1L, 10000, 2)
            )
        )

        // when
        val createdOrderId = orderService.createOrder(orderCreateRequest.userId, orderCreateRequest.toShipping(), *orderCreateRequest.orderProducts.toTypedArray())
        val foundOrder: OrderSearchResponse = orderService.findOrderById(createdOrderId)

        // then
        assertThat(orderCreateRequest.zipcode).isEqualTo(foundOrder.deliveryAddress.zipcode)
        assertThat(orderCreateRequest.address).isEqualTo(foundOrder.deliveryAddress.address)
        assertThat(orderCreateRequest.addressDetails).isEqualTo(foundOrder.deliveryAddress.addressDetails)
        assertThat(orderCreateRequest.orderMessage).isEqualTo(foundOrder.deliveryMessage)
    }
}