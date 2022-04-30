package com.study.commerce.shipping.service

import com.study.commerce.shipping.api.request.ShippingCreateRequest

interface ShippingService {
    fun createShipping(shippingCreateRequest: ShippingCreateRequest): Long
}