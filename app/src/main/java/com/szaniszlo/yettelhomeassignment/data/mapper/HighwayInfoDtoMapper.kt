package com.szaniszlo.yettelhomeassignment.data.mapper

import com.szaniszlo.yettelhomeassignment.data.dto.HighwayVignetteDto
import com.szaniszlo.yettelhomeassignment.data.dto.VehicleCategoryDto
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayVignette
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.Name
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.Payload
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.VehicleCategory

internal fun com.szaniszlo.yettelhomeassignment.data.dto.Payload.asDomain(): Payload {
    return Payload(
        highwayVignettes = highwayVignettes.map { it.asDomain() },
        vehicleCategories = vehicleCategories.map { it.asDomain() }
    )
}

internal fun HighwayVignetteDto.asDomain() =
    HighwayVignette(
        cost = cost,
        sum = sum,
        trxFee = trxFee,
        vignetteCategory = when (vehicleCategory) {
            "CAR" -> VignetteCategory.D1
            else -> throw IllegalArgumentException("Can not map vehicle category to vignette category: $vehicleCategory")
        },
        vignetteTypes = vignetteType.map { VignetteType.valueOf(it) }
    )

internal fun VehicleCategoryDto.asDomain() = VehicleCategory(
    name = Name(en = name.en,hu = name.hu),
    vignetteCategory = VignetteCategory.valueOf(vignetteCategory)
)