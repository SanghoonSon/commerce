package com.study.commerce.product.repository

import com.study.commerce.product.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByProductId(productId: Long): Product
}