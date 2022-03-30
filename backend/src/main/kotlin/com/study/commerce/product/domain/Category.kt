package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "Categories")
class Category(
    @Id @GeneratedValue
    var categoryId: Long? = null,

    @OneToMany(mappedBy = "Category")
    var categoryItem: List<CategoryItem>
)