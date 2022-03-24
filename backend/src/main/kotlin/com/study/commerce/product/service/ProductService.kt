package com.study.commerce.product.service

import com.study.commerce.product.domain.Product

interface ProductService {
    fun addProduct(product: Product): Product
    fun getProduct(): List<Product>
}
