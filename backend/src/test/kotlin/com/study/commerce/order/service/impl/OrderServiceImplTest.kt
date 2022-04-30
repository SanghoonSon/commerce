package com.study.commerce.order.service.impl

import com.study.commerce.order.api.request.OrderCreateRequest
import com.study.commerce.order.service.dto.OrderProductDto
import com.study.commerce.order.api.response.OrderDetailResponse
import com.study.commerce.order.service.OrderService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class OrderServiceImplTest{

    @Autowired
    private lateinit var orderService: OrderService

    @Test
    fun `주문_생성`() {
        // given
        val request = OrderCreateRequest(
            userId = 1L,
            zipcode = "123-456",
            address = "대전광역시 유성구",
            addressDetails = "예제동 1동 1호",
            shippingMessage = "문앞에 놔주세요",
            orderProducts = mutableListOf(
                OrderProductDto(1L, 10000, 2, 10000),
                OrderProductDto(2L, 15000, 3, 10000),
                OrderProductDto(3L, 20000, 1, 10000)
            ),
            placeToReceive = "문앞",
        )

        // when
        val createdOrderId = orderService.createOrder(
            request.userId,
            request.orderProducts
        )
        val orderDto = orderService.findOrderById(createdOrderId)
        val foundOrder: OrderDetailResponse = OrderDetailResponse.fromDto(orderDto)

        // then
        Assertions.assertThat(foundOrder != null)
    }
}