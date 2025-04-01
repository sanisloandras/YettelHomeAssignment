package com.szaniszlo.yettelhomeassignment.data.repository

import com.szaniszlo.yettelhomeassignment.data.dto.HighwayInfoDto
import com.szaniszlo.yettelhomeassignment.data.dto.HighwayOrdersDto
import com.szaniszlo.yettelhomeassignment.data.dto.Order
import com.szaniszlo.yettelhomeassignment.data.mapper.asDomain
import com.szaniszlo.yettelhomeassignment.data.mapper.mapCounties
import com.szaniszlo.yettelhomeassignment.data.network.HighwayApi
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.model.vehicle.Vehicle
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultHighwayRepository @Inject constructor(
    private val highwayApi: HighwayApi,
    private val countyRepository: CountyRepository,
) : HighwayRepository {

    // basic in-memory cache, could be abstracted as a data source
    private var cachedHighwayInfo: HighwayInfo? = null

    // basic in-memory cache, could be abstracted as a data source
    private var cachedVehicle: Vehicle? = null

    private var vignetteSelection = emptySet<VignetteType>()

    override suspend fun getHighwayInfo(): HighwayInfo {
        cachedHighwayInfo?.let { return it }
        return requireNotNull(highwayApi.info().body())
            .also { highwayInfoDto ->
                saveCounties(highwayInfoDto)
            }
            .let { highwayInfoDto ->
                HighwayInfo(
                    payload = highwayInfoDto.payload.asDomain(),
                    statusCode = highwayInfoDto.statusCode,
                )
            }
            .also { cachedHighwayInfo = it }
    }

    private fun saveCounties(highwayInfoDto: HighwayInfoDto) {
        countyRepository.saveCounties(
            counties = highwayInfoDto.mapCounties()
        )
    }

    override suspend fun getVehicle(): Vehicle {
        cachedVehicle?.let { return it }
        return requireNotNull(highwayApi.vehicle().body()).asDomain().also { cachedVehicle = it }
    }

    override fun saveVignetteSelection(vignetteSelection: Set<VignetteType>) {
        this.vignetteSelection = vignetteSelection
    }

    override fun getVignetteSelection() = vignetteSelection

    override suspend fun confirmOrder() {
        val category = getVehicle().vignetteCategory.category
        val highwayInfo = getHighwayInfo()
        val orders = HighwayOrdersDto(
            highwayOrders = vignetteSelection.map { vignetteType ->
                Order(
                    type = vignetteType.name,
                    category = category,
                    cost = highwayInfo.payload.highwayVignettes.first { highwayVignette ->
                        highwayVignette.vignetteTypes.contains(vignetteType)
                    }.cost,
                )
            }
        )
        highwayApi.order(orders)
    }
}