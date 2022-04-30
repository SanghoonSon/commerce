package com.study.commerce.shipping.api.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.study.commerce.shipping.domain.enums.PackingSize
import com.study.commerce.shipping.domain.enums.PackingType
import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.api.request.ShippingCreateRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@AutoConfigureMockMvc
internal class ShippingApiControllerV1Test {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `배송 생성 요청`() {
        // given
        var requestUri = "/api/v1/shipping"
        val shippingCreateRequest = ShippingCreateRequest(
            orderId = 1L,
            zipcode = "123-456",
            address = "대전광역시 유성구",
            addressDetails = "예제동 1동 1호",
            placeToReceive = "문앞",
            shippingMessage = "문앞에 놔주세요",
            packing = Packing(PackingType.PAPER, PackingSize.MIDDLE),
        )
        val shippingCreateRequestJson = jacksonObjectMapper().writeValueAsString(shippingCreateRequest)

        // when
        val response = mockMvc.perform(
            post(requestUri)
                .content(shippingCreateRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andDo(print())
            .andReturn().response

        // then
        assertEquals(HttpStatus.CREATED.value(), response.status, "생성 응답 코드는 201입니다.")
        assertNotNull(response.contentAsString, "배송 생성 후 ID를 리턴해줍니다.")
    }

}
