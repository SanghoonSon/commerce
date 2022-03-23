package com.study.commerce.product.service

import com.study.commerce.product.domain.Product

interface ProductService {
    fun addProduct(productId: Long, category: String, subCategory: String, productName: String, price: Int): Product
    fun getProduct(): List<Product>
}
