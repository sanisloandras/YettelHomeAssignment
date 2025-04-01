package com.szaniszlo.yettelhomeassignment.ui.countyvignettes

import com.szaniszlo.yettelhomeassignment.domain.model.county.County

sealed interface CountyVignettePurchaseUiState {
    data object Loading : CountyVignettePurchaseUiState
    data class Default(
        val counties: List<SelectableCounty>,
        val totalCost: Float,
        val isDirectConnectionPresent: Boolean,
    ) : CountyVignettePurchaseUiState
}

data class SelectableCounty(
    val county: County,
    val isSelected: Boolean,
) {
    val id = county.id
}