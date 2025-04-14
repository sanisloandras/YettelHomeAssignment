package com.szaniszlo.yettelhomeassignment.ui.main

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteCategory
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType

sealed interface MainVignettePurchaseUiState {
    data object Loading : MainVignettePurchaseUiState
    data object Error : MainVignettePurchaseUiState

    data class Default(
        val plate: String,
        val name: String,
        val vignetteCategory: VignetteCategory,
        val countryVignettes: List<CountryVignette>,
    ) : MainVignettePurchaseUiState
}

data class CountryVignette(
    val isSelected: Boolean,
    val vignetteType: VignetteType,
    val vignetteCategory: VignetteCategory,
    val cost: Float,
)
