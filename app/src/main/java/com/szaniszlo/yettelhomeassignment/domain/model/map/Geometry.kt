package com.szaniszlo.yettelhomeassignment.domain.model.map

data class Geometry(
    val coordinates: List<List<List<Double>>>,
    val type: String
)