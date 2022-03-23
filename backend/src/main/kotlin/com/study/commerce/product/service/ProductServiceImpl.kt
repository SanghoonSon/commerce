package com.study.commerce.product.service

import com.study.commerce.product.domain.Product
import com.study.commerce.product.repository.ProductRepository

class ProductServiceImpl(var productRepository: ProductRepository) : ProductService {

    override fun addProduct(
        productId: Long,
        category: String,
        subCategory: String,
        productName: String,
        price: Int
    ): Product {
        val product = productRepository.save(Product(productId, category, subCategory, productName, price))
        return product
    }

    override fun getProduct(): List<Product> {
        return productRepository.findAll()
    }
}