package com.szaniszlo.yettelhomeassignment.ui.countyvignettes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.usecase.GetCountiesWithCostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountyVignettePurchaseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getCountiesUseCase: GetCountiesWithCostUseCase,
) : ViewModel() {

    private val selection =
        savedStateHandle.getStateFlow(KEY_SELECTED_COUNTY_IDS,emptyList<String>())

    val uiState = combine(
        getCountiesUseCase(),
        selection
    ) { counties,selection ->
        val selectableCounties = counties.map { county ->
            SelectableCounty(
                county = county,
                isSelected = selection.contains(county.id)
            )
        }
        CountyVignettePurchaseUiState.Default(
            counties = selectableCounties,
            totalCost = selectableCounties
                .filter { it.isSelected }
                .fold(0f) { acc,county -> acc + county.county.cost },
            isDirectConnectionPresent = isDirectConnectionPresent(selectableCounties)
        )
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(),CountyVignettePurchaseUiState.Loading)

    private val _noCountySelectedEvent = MutableSharedFlow<Unit>()
    val noCountySelectedEvent = _noCountySelectedEvent.asSharedFlow()

    private val _navigateToConfirmOrder = MutableSharedFlow<String>()
    val navigateToConfirmOrder = _navigateToConfirmOrder.asSharedFlow()

    fun onCountyCheckChanged(countyId: String) {
        val selectedCountyIds =
            savedStateHandle.get<List<String>>(KEY_SELECTED_COUNTY_IDS) ?: emptyList()
        val updatedSelectedCountyIds = if (selectedCountyIds.contains(countyId)) {
            selectedCountyIds.minus(countyId)
        } else {
            selectedCountyIds.plus(countyId)
        }
        savedStateHandle[KEY_SELECTED_COUNTY_IDS] = updatedSelectedCountyIds
    }

    fun next() {
        viewModelScope.launch {
            val selectedCountyIds =
                savedStateHandle.get<List<String>>(KEY_SELECTED_COUNTY_IDS) ?: emptyList()
            when {
                selectedCountyIds.isEmpty() -> _noCountySelectedEvent.emit(Unit)
                else -> {
                    val vignetteTypes = selectedCountyIds.joinToString(",")
                    _navigateToConfirmOrder.emit(vignetteTypes)
                }
            }
        }
    }

    private fun isDirectConnectionPresent(selectableCounties: List<SelectableCounty>): Boolean {
        val selectedCounties = selectableCounties.filter { it.isSelected }.map { it.county.name }
        return when {
            selectedCounties.isEmpty() || selectedCounties.size == 1 -> true
            else -> selectedCounties.all { county ->
                val neighboursOfCounty = neighbourMap[county] ?: emptySet()
                selectedCounties.any { it in neighboursOfCounty }
            }
        }
    }

    companion object {
        private const val KEY_SELECTED_COUNTY_IDS = "selectedCountyIds"

        // should we use ids? it was simpler to get this info using county names... whatever... ideally this should come from api response
        private val neighbourMap = mapOf(
            "Bács-Kiskun" to setOf(
                "Baranya",
                "Tolna",
                "Fejér",
                "Pest",
                "Jász-Nagykun-Szolnok",
                "Csongrád"
            ),
            "Baranya" to setOf("Somogy","Tolna","Bács-Kiskun"),
            "Békés" to setOf("Csongrád","Jász-Nagykun-Szolnok","Hajdú-Bihar"),
            "Borsod-Abaúj-Zemplén" to setOf(
                "Nógrád",
                "Heves",
                "Jász-Nagykun-Szolnok",
                "Hajdú-Bihar",
                "Szabolcs-Szatmár-Bereg"
            ),
            "Csongrád" to setOf("Bács-Kiskun","Jász-Nagykun-Szolnok","Békés"),
            "Fejér" to setOf("Komárom-Esztergom","Pest","Bács-Kiskun","Tolna","Somogy","Veszprém"),
            "Győr-Moson-Sopron" to setOf("Vas","Veszprém","Komárom-Esztergom"),
            "Hajdú-Bihar" to setOf(
                "Békés",
                "Jász-Nagykun-Szolnok",
                "Borsod-Abaúj-Zemplén",
                "Szabolcs-Szatmár-Bereg"
            ),
            "Heves" to setOf(
                "Jász-Nagykun-Szolnok",
                "Pest",
                "Nógrád",
                "Borsod-Abaúj-Zemplén",
                "Hajdú-Bihar"
            ),
            "Jász-Nagykun-Szolnok" to setOf(
                "Csongrád",
                "Bács-Kiskun",
                "Pest",
                "Heves",
                "Hajdú-Bihar",
                "Békés"
            ),
            "Komárom-Esztergom" to setOf("Győr-Moson-Sopron","Veszprém","Fejér","Pest"),
            "Nógrád" to setOf("Pest","Heves","Borsod-Abaúj-Zemplén"),
            "Pest" to setOf(
                "Komárom-Esztergom",
                "Fejér",
                "Bács-Kiskun",
                "Jász-Nagykun-Szolnok",
                "Heves",
                "Nógrád"
            ),
            "Somogy" to setOf("Zala","Veszprém","Fejér","Tolna","Baranya"),
            "Szabolcs-Szatmár-Bereg" to setOf("Borsod-Abaúj-Zemplén","Hajdú-Bihar"),
            "Tolna" to setOf("Somogy","Baranya","Fejér","Bács-Kiskun"),
            "Vas" to setOf("Győr-Moson-Sopron","Veszprém","Zala"),
            "Veszprém" to setOf(
                "Somogy",
                "Zala",
                "Vas",
                "Győr-Moson-Sopron",
                "Komárom-Esztergom",
                "Fejér"
            ),
            "Zala" to setOf("Vas","Veszprém","Somogy"),
        )
    }
}