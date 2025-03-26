package com.szaniszlo.yettelhomeassignment.data.dto

internal data class HighwayInfoDto(
    val dataType: String,
    val payload: Payload,
    val requestId: Int,
    val statusCode: String
)

internal data class Payload(
    val counties: List<CountyDto>,
    val highwayVignettes: List<HighwayVignetteDto>,
    val vehicleCategories: List<VehicleCategoryDto>
)

internal data class CountyDto(
    val id: String,
    val name: String
)

internal data class HighwayVignetteDto(
    val cost: Float,
    val sum: Float,
    val trxFee: Float,
    val vehicleCategory: String,
    val vignetteType: List<String>
)

internal data class VehicleCategoryDto(
    val category: String,
    val name: NameDto,
    val vignetteCategory: String
)

internal data class NameDto(
    val en: String,
    val hu: String
)