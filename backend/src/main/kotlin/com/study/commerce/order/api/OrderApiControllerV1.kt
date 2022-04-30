package com.study.commerce.order.api

import com.study.commerce.order.api.request.OrderCreateRequest
import com.study.commerce.order.api.response.OrderDetailResponse
import com.study.commerce.order.service.OrderService
import com.study.commerce.order.service.dto.OrderDto
import com.study.commerce.shared.response.CommonApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
@Api(tags = ["orders"])
class OrderApiControllerV1(
    private val orderService: OrderService
) {

    @GetMapping("/{orderId}")
    @ApiOperation(value = "주문 상세 조회", notes = "주문 Id를 이용하여 주문 정보를 조회합니다.")
    fun getOrderDetails(@PathVariable orderId: Long): CommonApiResponse<OrderDetailResponse> {
        val orderDto = orderService.findOrderById(orderId)
        return CommonApiResponse(OrderDetailResponse.fromDto(orderDto), HttpStatus.OK)
    }

    @PostMapping
    @ApiOperation(value = "주문 생성", notes = "신규 주문을 생성합니다.")
    fun createOrderRequest(@RequestBody orderCreateRequest: OrderCreateRequest): CommonApiResponse<Long> {
        val newOrderId = orderService.createOrder(orderCreateRequest.userId, orderCreateRequest.orderProducts)
        orderService.requestToCreateShipping(newOrderId, orderCreateRequest.toShippingAddress())
        return CommonApiResponse(newOrderId, HttpStatus.CREATED, "주문이 생성되었습니다.")
    }
}