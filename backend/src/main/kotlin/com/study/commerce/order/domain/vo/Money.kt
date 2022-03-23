package com.study.commerce.order.domain.vo

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Money(
    @Column(nullable = false, updatable = false)
    val price: Int,

    @Column(nullable = false, updatable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val currency: Currency
) {
    companion object {
        var ZERO_KRW = Money(0, Currency.KRW)
    }

    fun plus(price: Int): Money {
        return Money(this.price + price, this.currency)
    }

    fun minus(price: Int): Money {
        return Money(this.price - price, this.currency)
    }
}