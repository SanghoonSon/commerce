package com.study.commerce.product.domain

import javax.persistence.*

@Entity
@Table(name = "Categories")
class Category(
    @Id @GeneratedValue
    @Column(name ="category_id")
    var categoryId: Long? = null,

    @OneToMany(mappedBy = "Category")
    @JoinColumn(name = "categoryItemId")
    var categoryItem: List<CategoryItem>
)