package com.szaniszlo.yettelhomeassignment.data.dto

internal data class HighwayOrdersDto(
    val highwayOrders: List<Order>
)

internal data class Order(
    val type: String,
    val category: String,
    val cost: Float,
)