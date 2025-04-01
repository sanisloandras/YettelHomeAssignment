package com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType

data class HighwayVignette(
    val cost: Float,
    val sum: Float,
    val trxFee: Float,
    val vignetteCategory: VignetteCategory,
    val vignetteTypes: List<VignetteType>
)