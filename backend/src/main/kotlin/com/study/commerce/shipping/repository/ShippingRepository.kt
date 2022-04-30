package com.study.commerce.shipping.repository

import com.study.commerce.shipping.domain.entity.Shipping
import org.springframework.data.jpa.repository.JpaRepository

interface ShippingRepository: JpaRepository<Shipping, Long> {
}