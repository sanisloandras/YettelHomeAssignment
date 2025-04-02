package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation.OrderConfirmationData
import com.szaniszlo.yettelhomeassignment.domain.model.orderconfirmation.OrderVignetteType
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class GetOrderConfirmationDataUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
    private val countyRepository: CountyRepository,
) {
    suspend operator fun invoke(vignetteSelection: Set<VignetteType>): OrderConfirmationData {
        return supervisorScope {
            val highwayInfoDef = async { highwayRepository.getHighwayInfo() }
            val vehicleDef = async { highwayRepository.getVehicle() }
            val highwayInfo = highwayInfoDef.await()
            val vehicle = vehicleDef.await()
            val counties = countyRepository.getCounties().first()

            val orderVignetteType = mapOrderVignetteType(counties, vignetteSelection)

            val countyVignettes = counties
                .filter { vignetteSelection.contains(VignetteType.valueOf(it.id)) }

            val trxFee = getTrxFee(countyVignettes, highwayInfo, vignetteSelection)

            // trxFee is applied only once
            val totalCost =
                calculateTotalCost(countyVignettes, highwayInfo, vignetteSelection, trxFee)

            OrderConfirmationData(
                plate = vehicle.plate,
                orderVignetteType = orderVignetteType,
                counties = countyVignettes,
                totalCost = totalCost,
                trxFee = trxFee
            )
        }
    }

    private fun calculateTotalCost(
        countyVignettes: List<County>,
        highwayInfo: HighwayInfo,
        vignetteSelection: Set<VignetteType>,
        trxFee: Float
    ) = if (countyVignettes.isNotEmpty()) {
        countyVignettes.fold(0f) { acc, county -> acc + county.cost }
    } else {
        highwayInfo.payload.highwayVignettes.first { highwayVignette ->
            highwayVignette.vignetteTypes.intersect(vignetteSelection).isNotEmpty()
        }.cost
    } + trxFee

    private fun getTrxFee(
        countyVignettes: List<County>,
        highwayInfo: HighwayInfo,
        vignetteSelection: Set<VignetteType>
    ) = if (countyVignettes.isNotEmpty()) {
        countyVignettes.first().trxFee
    } else {
        highwayInfo.payload.highwayVignettes.first { highwayVignette ->
            highwayVignette.vignetteTypes.intersect(vignetteSelection).isNotEmpty()
        }.trxFee
    }

    private fun mapOrderVignetteType(
        counties: List<County>,
        vignetteSelection: Set<VignetteType>
    ) = when {
        counties.map { VignetteType.valueOf(it.id) }
            .intersect(vignetteSelection)
            .isNotEmpty() -> OrderVignetteType.YEAR_COUNTY

        vignetteSelection.contains(VignetteType.YEAR) -> OrderVignetteType.YEAR
        vignetteSelection.contains(VignetteType.MONTH) -> OrderVignetteType.MONTH
        vignetteSelection.contains(VignetteType.WEEK) -> OrderVignetteType.WEEK
        vignetteSelection.contains(VignetteType.DAY) -> OrderVignetteType.DAY
        else -> throw IllegalArgumentException("Can not map vignette selection $vignetteSelection to order vignette type.")
    }
}