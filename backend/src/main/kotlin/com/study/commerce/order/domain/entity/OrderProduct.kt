package com.study.commerce.order.domain.entity

import com.study.commerce.product.domain.Product
import com.study.commerce.shared.domain.BaseEntity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    lateinit var order: Order

    fun addProduct(quantity: Int) {
        this.quantity += quantity
    }

    fun getTotalPrice(): Int {
        return this.price * this.quantity
    }

    fun changeQuantity(quantity: Int) {
        this.quantity = quantity;
    }

    fun cancel() {
        // TODO shshon: 상품 수량 증가 필요
    }
}
