package com.szaniszlo.yettelhomeassignment.domain.repository

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.model.vehicle.Vehicle
import kotlinx.coroutines.flow.Flow

interface HighwayRepository {
    suspend fun getHighwayInfo(): HighwayInfo
    suspend fun getVehicle(): Vehicle
    fun saveVignetteSelection(vignetteSelection: Set<VignetteType>)
    fun getVignetteSelection(): Set<VignetteType>
    suspend fun confirmOrder()
}