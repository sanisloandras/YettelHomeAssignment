package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import javax.inject.Inject

class ConfirmOrderUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
) {

    suspend operator fun invoke(vignetteSelection: Set<VignetteType>) {
        highwayRepository.confirmOrder(vignetteSelection)
    }
}