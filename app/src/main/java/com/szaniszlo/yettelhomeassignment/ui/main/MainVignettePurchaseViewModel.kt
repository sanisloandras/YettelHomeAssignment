package com.szaniszlo.yettelhomeassignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetHighwayInfoUseCase
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainVignettePurchaseViewModel @Inject constructor(
    private val getHighwayInfoUseCase: GetHighwayInfoUseCase,
    private val getVehicleUseCase: GetVehicleUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<MainVignettePurchaseUiState>(MainVignettePurchaseUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navigateToPurchaseConfirmationEvent = MutableSharedFlow<String>()
    val navigateToPurchaseConfirmationEvent = _navigateToPurchaseConfirmationEvent.asSharedFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.value = MainVignettePurchaseUiState.Loading
            supervisorScope {
                try {
                    val highwayInfoDeferred = async { getHighwayInfoUseCase() }
                    val vehicleDeferred = async { getVehicleUseCase() }
                    val highwayInfo = highwayInfoDeferred.await()
                    val vehicle = vehicleDeferred.await()
                    _uiState.value = MainVignettePurchaseUiState.Default(
                        plate = vehicle.plate,
                        name = vehicle.name,
                        vignetteCategory = vehicle.vignetteCategory,
                        countryVignettes = highwayInfo.payload.highwayVignettes
                            // todo figure out if this business logic is correct
                            .filter { !it.vignetteTypes.contains(VignetteType.YEAR) }
                            .filter { it.vignetteCategory == vehicle.vignetteCategory }
                            .filter { it.vignetteTypes.intersect(NON_COUNTY_VIGNETTES).isNotEmpty() }
                            .mapIndexed { index, highwayVignette ->
                                CountryVignette(
                                    isSelected = (index == 0),
                                    vignetteType = highwayVignette.vignetteTypes.first(),
                                    vignetteCategory = highwayVignette.vignetteCategory,
                                    cost = highwayVignette.cost,
                                )
                            }
                    )
                } catch (exception: IOException) {
                    Timber.e(exception)
                    _uiState.value = MainVignettePurchaseUiState.Error
                }
            }
        }
    }

    fun onCountryVignetteClicked(countryVignette: CountryVignette) {
        _uiState.update { mainVignettePurchaseUiState ->
            if (mainVignettePurchaseUiState is MainVignettePurchaseUiState.Default) {
                mainVignettePurchaseUiState.copy(
                    countryVignettes = mainVignettePurchaseUiState.countryVignettes
                        .map { it.copy(isSelected = it.vignetteType == countryVignette.vignetteType) }
                )
            } else {
                mainVignettePurchaseUiState
            }
        }
    }

    fun purchase() {
        viewModelScope.launch {
            val state =
                _uiState.value as? MainVignettePurchaseUiState.Default ?: return@launch
            val vignetteSelection = state.countryVignettes
                .first { it.isSelected }
                .vignetteType.name
            _navigateToPurchaseConfirmationEvent.emit(vignetteSelection)
        }
    }

    companion object {
        private val NON_COUNTY_VIGNETTES = setOf(
            VignetteType.DAY,
            VignetteType.WEEK,
            VignetteType.MONTH,
            VignetteType.YEAR,
        )
    }
}