package com.szaniszlo.yettelhomeassignment.data.mapper

import com.szaniszlo.yettelhomeassignment.data.dto.VehicleDto
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory
import com.szaniszlo.yettelhomeassignment.domain.model.vehicle.Vehicle

internal fun VehicleDto.asDomain() = Vehicle(
    name = name,
    plate = plate,
    vignetteCategory = VignetteCategory.valueOf(vignetteType),
)