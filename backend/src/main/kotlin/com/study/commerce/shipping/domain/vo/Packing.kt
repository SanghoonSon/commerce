package com.study.commerce.shipping.domain.vo

import com.study.commerce.shipping.domain.enums.PackingSize
import com.study.commerce.shipping.domain.enums.PackingType
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class Packing(
    @Column
    @Enumerated(EnumType.STRING)
    val packingType: PackingType,

    @Column
    @Enumerated(EnumType.STRING)
    val packingSize: PackingSize
)