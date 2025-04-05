package com.szaniszlo.yettelhomeassignment.domain.model.map

data class GeoJson(
    val features: List<Feature>,
    val type: String
)