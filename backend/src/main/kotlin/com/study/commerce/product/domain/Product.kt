package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue
    var productId: Long? = null,
    var category: String,
    var subCategory: String,
    var productName: String,
    var price: Int
)
