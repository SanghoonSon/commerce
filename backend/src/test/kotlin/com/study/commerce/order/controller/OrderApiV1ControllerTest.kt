package com.study.commerce.order.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.study.commerce.order.dto.OrderCreateRequest
import com.study.commerce.order.dto.OrderProductDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@AutoConfigureMockMvc
internal class OrderApiV1ControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `주문 생성 요청`() {
        // given
        val requestUri = "/api/v1/orders"
        val orderCreateRequest = OrderCreateRequest(
            userId = 1L,
            zipcode = "123-456",
            address = "대전광역시 유성구",
            addressDetails = "예제동 1동 1호",
            orderMessage = "문앞에 놔주세요",
            orderProducts = mutableListOf(
                OrderProductDto(1L, 10000, 2),
                OrderProductDto(2L, 15000, 5),
                OrderProductDto(3L, 13000, 1)
            )
        )
        val orderCreateRequestJson = jacksonObjectMapper().writeValueAsString(orderCreateRequest);

        // when
        val response = mockMvc.perform(
            post(requestUri)
                .content(orderCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andDo(print())
            .andReturn().response

        // then
    }

    @Test
    fun `주문 상세 조회`() {
        // given
        val requestUri = "/api/v1/orders/1"

        // when
        mockMvc.perform(get(requestUri))
            .andExpect(status().isOk)
            .andDo(print())
    }
}