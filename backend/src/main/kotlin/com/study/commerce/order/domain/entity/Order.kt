package com.study.commerce.order.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.study.commerce.order.domain.vo.Money
import com.study.commerce.product.domain.Product
import com.study.commerce.shared.domain.BaseEntity
import com.study.commerce.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    // TODO shshon : 주문자 추가 필요
//    user: User,
    shipping: Shipping,
    vararg orderProducts: OrderProduct
) : BaseEntity() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    var id: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "order")
    @JsonIgnoreProperties(value = ["order"])
    var orderProducts: MutableList<OrderProduct> = mutableListOf(*orderProducts)
        protected set

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.PENDING

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "price", column = Column(name = "total_cost")),
        AttributeOverride(name = "currency", column = Column(name = "total_cost_currency"))
    )
    var totalCost: Money = Money.ZERO_KRW


    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "shipping_id")
    var shipping: Shipping = shipping
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User ? = null
        protected set

    fun changeProductQuantity(productId: Long, quantity: Int) {
        this.orderProducts.find { it.product.productId == productId }?.changeQuantity(quantity)
    }

    fun addProduct(orderProduct: OrderProduct) {
        // 주문서에 해당 제품이 있으면 수량을 추가하고, 없으면 리스트 목록에 추가한다.
        this.orderProducts.find { it.product.productId == orderProduct.product.productId }
            ?.also {
                it.addProduct(orderProduct.quantity)
            }
            ?: run {
                orderProduct.order = this
                this.orderProducts.add(orderProduct)
            }
    }

    fun cancelProduct(product: Product) {
        val toBeCanceledOrderProduct = this.orderProducts.first { it.product.productId == product.productId }
        toBeCanceledOrderProduct.cancel()
    }
}