package com.szaniszlo.yettelhomeassignment.ui.orderconfirmation

import com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation.OrderConfirmationData

sealed interface OrderConfirmationUiState {
    data object Loading : OrderConfirmationUiState
    data object Error : OrderConfirmationUiState

    data class Default(
        val orderConfirmationData: OrderConfirmationData,
    ) : OrderConfirmationUiState
}