package com.szaniszlo.yettelhomeassignment.ui.orderconfirmation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.usecase.ConfirmOrderUseCase
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetOrderConfirmationDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getOrderConfirmationDataUseCase: GetOrderConfirmationDataUseCase,
    private val confirmOrderUseCase: ConfirmOrderUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<OrderConfirmationUiState>(OrderConfirmationUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _successfulOrder = MutableSharedFlow<Unit>()
    val successfulOrder = _successfulOrder.asSharedFlow()

    init {
        fetchOrderConfirmationData()
    }

    private fun fetchOrderConfirmationData() {
        _uiState.value = OrderConfirmationUiState.Loading
        viewModelScope.launch {
            try {
                val vignetteSelection = getVignetteSelection()
                _uiState.value = OrderConfirmationUiState.Default(
                    orderConfirmationData = getOrderConfirmationDataUseCase(vignetteSelection)
                )
            } catch (ioException: IOException) {
                Timber.e(ioException)
                _uiState.value = OrderConfirmationUiState.Error
            }
        }
    }

    fun confirmOrder() {
        _uiState.value = OrderConfirmationUiState.Loading
        viewModelScope.launch {
            try {
                confirmOrderUseCase(getVignetteSelection())
                _successfulOrder.emit(Unit)
            } catch (ioException: IOException) {
                Timber.e(ioException)
                _uiState.value = OrderConfirmationUiState.Error
            }
        }
    }

    private fun getVignetteSelection(): Set<VignetteType> =
        requireNotNull(savedStateHandle.get<String>(KEY_VIGNETTE_SELECTION))
            .split(",")
            .map { VignetteType.valueOf(it) }
            .toSet()

    companion object {
        private const val KEY_VIGNETTE_SELECTION = "vignetteSelection"
    }
}