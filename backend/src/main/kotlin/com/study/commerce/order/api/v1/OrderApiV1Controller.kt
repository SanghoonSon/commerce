package com.study.commerce.order.api.v1

import com.study.commerce.order.dto.OrderCreateRequest
import com.study.commerce.order.dto.OrderSearchResponse
import com.study.commerce.order.service.OrderService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
@Api(tags = ["orders"])
class OrderApiV1Controller(
    private val orderService: OrderService
) {

    @GetMapping("/{orderId}")
    @ApiOperation(value = "주문 상세 조회", notes = "주문 Id를 이용하여 주문 정보를 조회합니다.")
    fun getOrderDetails(@PathVariable orderId: Long): ResponseEntity<OrderSearchResponse> {
        val orderSearchDto = orderService.findOrderById(orderId)
        return ResponseEntity.status(HttpStatus.FOUND).body(orderSearchDto)
    }

    @PostMapping
    @ApiOperation(value = "주문 생성", notes = "신규 주문을 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrderRequest(@RequestBody orderCreateRequest: OrderCreateRequest) {
        orderService.createOrder(orderCreateRequest.userId, orderCreateRequest.toShipping(), *orderCreateRequest.orderProducts.toTypedArray())
    }
}