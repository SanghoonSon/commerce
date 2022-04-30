package com.study.commerce.shared.domain.vo

import com.study.commerce.shared.domain.enums.Currency
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Money(
    @Column(nullable = false, updatable = false)
    val price: Int,

    @Column(length = 5, nullable = false, updatable = false)
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