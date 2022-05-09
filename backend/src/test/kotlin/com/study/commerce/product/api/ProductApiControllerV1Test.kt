package com.study.commerce.product.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@AutoConfigureMockMvc
internal class ProductApiControllerV1Test {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `상품 조회`() {
        // given
        val requestUri = "/api/v1/products/1,3,5"

        // when
        mockMvc.perform(get(requestUri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }

    @Test
    fun `재고 상품 조회`() {
        // given
        val requestUri = "/api/v1/products/stock"

        // when
        mockMvc.perform(get(requestUri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }
}