package com.study.commerce.product.service.dto

import com.study.commerce.product.domain.Product
import com.study.commerce.product.domain.ProductStatus

data class ProductDto(
    var id: Long,
    var name: String,
    var status: ProductStatus,
    var price: Int
) {
    companion object {
        fun fromEntity(product: Product): ProductDto = ProductDto(
            id = product.productId!!,
            name = product.productName,
            status = product.productStatus,
            price = product.price
        )
    }
}