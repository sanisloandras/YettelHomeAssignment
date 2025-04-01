package com.szaniszlo.yettelhomeassignment.data.dto

internal data class VehicleDto(
    val country: Country,
    val internationalRegistrationCode: String,
    val name: String,
    val plate: String,
    val requestId: Int,
    val statusCode: String,
    val type: String,
    val vignetteType: String
)

internal data class Country(
    val en: String,
    val hu: String
)