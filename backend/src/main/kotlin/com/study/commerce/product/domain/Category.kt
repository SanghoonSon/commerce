package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "Categories")
class Category(
    @Id @GeneratedValue
    @Column(name ="category_id")
    val categoryId: Long? = null,

    @OneToMany(mappedBy = "category")
    val categoryItem: List<CategoryItem>
)