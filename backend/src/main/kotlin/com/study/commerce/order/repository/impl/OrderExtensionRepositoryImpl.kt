package com.study.commerce.order.repository.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.study.commerce.order.domain.entity.Order
import com.study.commerce.order.domain.entity.QOrder.order
import com.study.commerce.order.domain.entity.QOrderProduct.orderProduct
import com.study.commerce.order.repository.OrderExtensionRepository
import com.study.commerce.shipping.domain.entity.QShipping.shipping


class OrderExtensionRepositoryImpl(private val query: JPAQueryFactory) : OrderExtensionRepository{

    override fun findOrderWithAllEntitiesById(id: Long): Order? {
        val order = query.selectFrom(order)
            .join(order.shipping, shipping)
            .fetchJoin()
            .where(order.id.eq(id))
            .fetchOne()

        order!!.orderProducts
        return order
    }
}