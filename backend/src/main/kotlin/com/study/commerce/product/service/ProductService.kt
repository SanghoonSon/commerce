package com.study.commerce.product.service

import com.study.commerce.product.domain.Product
import com.study.commerce.product.service.dto.ProductDto

interface ProductService {
    fun addProduct(product: Product): String
    fun getProduct(): List<Product>
    fun getProducts(productIds: List<Long>): List<ProductDto>
    fun deleteProduct(productId:Long)
}
