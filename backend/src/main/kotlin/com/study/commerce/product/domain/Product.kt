package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "products")

class Product(
    @Id @GeneratedValue
    val productId: Long? = null,

    @OneToMany(mappedBy = "product")
    val categoryItem: List<CategoryItem>,

    var subCategory: String,
    var productName: String,

    @Enumerated(EnumType.STRING)
    val productStatus: ProductStatus = ProductStatus.Exist,
    val price: Int
)