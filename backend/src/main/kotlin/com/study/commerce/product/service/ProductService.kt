package com.study.commerce.product.service

import com.study.commerce.product.domain.Product

interface ProductService {
    fun addProduct(product: Product): String
    fun getProduct(): List<Product>
    fun deleteProduct(productId:Long)
}
