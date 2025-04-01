package com.szaniszlo.yettelhomeassignment.domain.model.vehicle

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory

data class Vehicle(
    val name: String,
    val plate: String,
    val vignetteCategory: VignetteCategory,
)