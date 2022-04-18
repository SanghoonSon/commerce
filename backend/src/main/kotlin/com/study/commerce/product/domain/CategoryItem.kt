package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "CategoryItems")
class CategoryItem(

    @Id @GeneratedValue
    var categoryItemId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    var product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    var categoryId: Category
)