package com.study.commerce.product.service

import com.study.commerce.product.domain.Product
import com.study.commerce.product.repository.ProductRepository

class ProductServiceImpl(var productRepository: ProductRepository) : ProductService {

    override fun addProduct(product: Product): Product {
        val product = productRepository.save(product)
        return product
    }

    override fun getProduct(): List<Product> {
        return productRepository.findAll()
    }
}