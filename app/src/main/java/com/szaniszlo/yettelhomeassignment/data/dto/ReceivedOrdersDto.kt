package com.szaniszlo.yettelhomeassignment.data.dto

internal data class ReceivedOrdersDto(
    val receivedOrders: List<ReceivedOrder>,
    val statusCode: String
)

internal data class ReceivedOrder(
    val category: String,
    val cost: Int,
    val type: String
)