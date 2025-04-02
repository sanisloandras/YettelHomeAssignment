package com.szaniszlo.yettelhomeassignment.domain.repository

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.model.vehicle.Vehicle

interface HighwayRepository {
    suspend fun getHighwayInfo(): HighwayInfo
    suspend fun getVehicle(): Vehicle
    suspend fun confirmOrder(vignetteSelection: Set<VignetteType>)
}