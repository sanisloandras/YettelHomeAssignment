package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import javax.inject.Inject

class GetVehicleUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
) {
    suspend operator fun invoke() = highwayRepository.getVehicle()
}