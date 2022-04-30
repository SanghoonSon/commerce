package com.study.commerce.shipping.domain.entity

import com.study.commerce.shared.domain.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "shipping_company")
class ShippingCompany(
    name: String,
    businessRegistrationNumber: String
): BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ?= null

    @Column(length = 20)
    var name: String = name;

    @Column(length = 30)
    var businessRegistrationNumber: String = businessRegistrationNumber
}