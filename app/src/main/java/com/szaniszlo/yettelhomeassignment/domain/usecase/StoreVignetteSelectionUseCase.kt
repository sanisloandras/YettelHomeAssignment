package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.model.VignetteType
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import javax.inject.Inject

class StoreVignetteSelectionUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
) {
    operator fun invoke(vignetteSelection: Set<VignetteType>) {
        highwayRepository.saveVignetteSelection(vignetteSelection)
    }
}