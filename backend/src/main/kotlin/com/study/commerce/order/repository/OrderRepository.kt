package com.study.commerce.order.repository

import com.study.commerce.order.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long> {
}