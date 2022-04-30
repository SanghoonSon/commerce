package com.study.commerce.order.repository

import com.study.commerce.order.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderExtensionRepository {
    fun findOrderWithAllEntitiesById(id: Long) : Order?
}

interface OrderRepository: JpaRepository<Order, Long>, OrderExtensionRepository