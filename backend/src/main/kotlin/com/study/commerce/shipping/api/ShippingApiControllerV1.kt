package com.study.commerce.shipping.api

import com.study.commerce.shipping.api.request.ShippingCreateRequest
import com.study.commerce.shipping.service.ShippingService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/shipping")
@Api(tags = ["shipping"])
class ShippingApiControllerV1(
    private val shippingService: ShippingService
) {

    @PostMapping
    @ApiOperation(value = "배송 생성", notes = "신규 배송을 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    fun createShippingRequest(@RequestBody createRequest: ShippingCreateRequest): Mono<ResponseEntity<Any>> {
        val newShippingId = shippingService.createShipping(createRequest)

        val location = UriComponentsBuilder.fromPath("/api/v1/shipping/{shippingId}").build(newShippingId)

        return Mono.just(ResponseEntity.created(location).body(newShippingId))
    }
}