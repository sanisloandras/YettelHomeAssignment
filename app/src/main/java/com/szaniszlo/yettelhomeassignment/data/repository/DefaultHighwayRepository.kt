package com.szaniszlo.yettelhomeassignment.data.repository

import com.szaniszlo.yettelhomeassignment.data.network.HighwayApi
import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.model.vehicle.Vehicle
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultHighwayRepository @Inject constructor(
    private val highwayApi: HighwayApi,
) : HighwayRepository {

    override suspend fun getHighwayInfo(): HighwayInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getVehicle(): Vehicle {
        TODO("Not yet implemented")
    }

    override fun saveVignetteSelection(vignetteSelection: Set<VignetteType>) {
        TODO("Not yet implemented")
    }

    override fun getVignetteSelection(): Set<VignetteType> {
        TODO("Not yet implemented")
    }

    override suspend fun confirmOrder() {
        TODO("Not yet implemented")
    }

}