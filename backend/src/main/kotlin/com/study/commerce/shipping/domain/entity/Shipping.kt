package com.study.commerce.shipping.domain.entity

import com.study.commerce.order.domain.entity.Order
import com.study.commerce.shared.domain.BaseEntity
import com.study.commerce.shipping.domain.enums.ShippingStatus
import com.study.commerce.shipping.domain.vo.Packing
import com.study.commerce.shipping.domain.vo.ShippingAddress
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Shipping(
    order: Order,
    address: ShippingAddress,
    message: String,
    packing: Packing
): BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    var id: Long? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order:Order = order
        set(orderParam) {
            field = orderParam
            orderParam.shipping = this;
        }

    @Embedded
    var address: ShippingAddress = address

    @Embedded
    val packing: Packing = packing

    @Column
    var message: String = message

    var shippingStartDate: LocalDateTime? = null

    var shippingCompleteDate: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var shippingStatus: ShippingStatus = ShippingStatus.PROCESSING
}