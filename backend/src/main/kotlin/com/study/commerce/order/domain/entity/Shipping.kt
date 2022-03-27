package com.study.commerce.order.domain.entity

import com.study.commerce.order.domain.vo.Address
import com.study.commerce.shared.domain.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Shipping(
    @Embedded
    var address: Address,
    var message: String
): BaseEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    var id: Long? = null

    var fee: Int? = null

    var shippingDate: LocalDateTime? = null

    @Enumerated(EnumType.STRING)
    var shippingStatus: ShippingStatus = ShippingStatus.PROCESSING
}