package com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo

data class Payload(
    val highwayVignettes: List<HighwayVignette>,
    val vehicleCategories: List<VehicleCategory>
)