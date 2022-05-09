package com.study.commerce.product.api

import com.study.commerce.product.api.response.ProductDetailResponse
import com.study.commerce.product.service.ProductService
import com.study.commerce.product.service.dto.ProductDto
import com.study.commerce.shared.response.CommonApiResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
@Api(tags = ["products"])
class ProductApiControllerV1(private val productService: ProductService) {

    @GetMapping(value = ["/products/stock"], consumes = ["application/json"])
    @ApiOperation(value = "재고가 있는 모든 상품 조회", notes = "재고가 있는 제품을 모두 조회합니다.")
    fun getCanBeOrderedProducts(): CommonApiResponse<List<ProductDetailResponse>> {
        val products = productService.getProduct()
            .map { ProductDto.fromEntity(it) }
            .map { ProductDetailResponse.fromDto(it) }
            .toList()
        return CommonApiResponse(products)
    }

    @GetMapping(value = ["/products/{productIds}"], consumes = ["application/json"])
    @ApiOperation(value = "지정 된 상품 조회", notes = "요청한 모든 제품에 대하여 상세 정보를 조회합니다.")
    fun getProductDetailsRequest(@PathVariable productIds: List<Long>): CommonApiResponse<List<ProductDetailResponse>> {
        val productDetailResponses = productService.getProducts(productIds)
            .map { ProductDetailResponse.fromDto(it) }
            .toList()
        return CommonApiResponse(productDetailResponses)
    }
}