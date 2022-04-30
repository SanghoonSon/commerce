package com.study.commerce.order.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.study.commerce.order.domain.enums.OrderStatus
import com.study.commerce.product.domain.Product
import com.study.commerce.shared.domain.BaseEntity
import com.study.commerce.shared.domain.enums.Currency
import com.study.commerce.shared.domain.vo.Money
import com.study.commerce.shipping.domain.entity.Shipping
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(userId: Long) : BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long? = null

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.PENDING

    var userId: Long = userId
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "order")
    @JsonIgnoreProperties(value = ["order"])
    var orderProducts: MutableList<OrderProduct> = mutableListOf()
        protected set

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    var shipping: Shipping? = null

    fun changeProductQuantity(productId: Long, quantity: Int) {
        this.orderProducts.find { it.product.productId == productId }?.changeQuantity(quantity)
    }

    fun addProducts(vararg orderProducts: OrderProduct) {
        if(orderProducts.isEmpty()) {
            throw IllegalArgumentException("Order Products should be not empty.")
        }

        orderProducts.forEach { newOrderProduct ->
            // 주문서에 해당 제품이 있으면 수량을 추가하고, 없으면 리스트 목록에 추가한다.
            this.orderProducts.find { it.product.productId == newOrderProduct.product.productId }
                ?.also {
                    it.increaseQuantity(newOrderProduct.quantity)
                }
                ?: run {
                    newOrderProduct.order = this
                    this.orderProducts.add(newOrderProduct)
                }
        }
    }

    fun cancelProduct(product: Product) {
        val toBeCanceledOrderProduct = this.orderProducts.first { it.product.productId == product.productId }
        toBeCanceledOrderProduct.cancel()
    }

    fun getTotalCost(): Money {
        return Money(this.orderProducts.sumOf { it.totalCost.price }, Currency.KRW)
    }
}