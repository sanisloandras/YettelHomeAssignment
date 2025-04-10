package com.szaniszlo.yettelhomeassignment.domain.model.map

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)