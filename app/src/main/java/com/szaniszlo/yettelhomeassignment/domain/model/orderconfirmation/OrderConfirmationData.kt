package com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation

import com.szaniszlo.yettelhomeassignment.domain.model.county.County

data class OrderConfirmationData(
    val plate: String,
    val orderVignetteType: OrderVignetteType,
    val counties: List<County>,
    val trxFee: Float,
    val totalCost: Float,
)