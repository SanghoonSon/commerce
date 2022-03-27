package com.study.commerce.product.service

import com.study.commerce.product.domain.Product
import org.springframework.stereotype.Component

@Component
interface ProductService {
    fun addProduct(productId: Int, category:String,subCategory: String,productName: String,price: String): Product
    fun getProduct(): List<Product>
}