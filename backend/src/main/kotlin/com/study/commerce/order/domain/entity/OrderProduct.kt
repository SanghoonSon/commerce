package com.study.commerce.order.domain.entity

import com.study.commerce.order.domain.enums.OrderProductStatus
import com.study.commerce.product.domain.Product
import com.study.commerce.shared.domain.BaseEntity
import com.study.commerce.shared.domain.enums.Currency
import com.study.commerce.shared.domain.vo.Money
import javax.persistence.*

@Entity
@Table(name = "order_products")
class OrderProduct(
    product: Product,
    quantity: Int,
    price: Int
): BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product = product
        protected set

    @Column(length = 5)
    var quantity: Int = quantity
        protected set

    var price: Int = price
        protected set

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "price", column = Column(name = "total_cost")),
        AttributeOverride(name = "currency", column = Column(name = "total_cost_currency", length = 5))
    )
    var totalCost: Money = Money(this.price * this.quantity, Currency.KRW)

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    var status: OrderProductStatus = OrderProductStatus.PENDING

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    lateinit var order: Order

    fun increaseQuantity(quantity: Int) {
        this.quantity += quantity
        this.totalCost = Money(this.price * this.quantity, Currency.KRW)
    }

    fun changeQuantity(quantity: Int) {
        this.quantity = quantity;
    }

    fun cancel() {
        // TODO shshon: 상품 수량 증가 필요
        this.status = OrderProductStatus.CANCELLED
    }
}
