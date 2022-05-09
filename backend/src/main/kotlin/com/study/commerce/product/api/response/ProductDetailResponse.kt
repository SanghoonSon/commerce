package com.study.commerce.product.api.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.study.commerce.product.domain.ProductStatus
import com.study.commerce.product.service.dto.ProductDto

@JsonInclude(JsonInclude.Include.NON_NULL)
class ProductDetailResponse (
    @JsonProperty(value = "productId", required = true)
    var id: Long,

    var name: String,

    @JsonProperty(required = true)
    var productStatus: ProductStatus,

    val price: Int
) {
    companion object {
        fun fromDto(productDto: ProductDto): ProductDetailResponse = ProductDetailResponse(
            id = productDto.id,
            name = productDto.name,
            productStatus = productDto.status,
            price = productDto.price
        )
    }
}