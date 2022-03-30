package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "products")

class  Product(
    @Id @GeneratedValue
    var productId: Long? = null,

    @OneToMany(mappedBy = "Product")
    var categoryItem: List<CategoryItem>,

    var subCategory: String,
    var productName: String,

    @Enumerated(EnumType.STRING)
    val productStatus: ProductStatus = ProductStatus.Exist,
    val price: Int
)
