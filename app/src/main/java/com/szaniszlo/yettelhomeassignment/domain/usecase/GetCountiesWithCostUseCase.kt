package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetCountiesWithCostUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
    private val countyRepository: CountyRepository,
) {

    operator fun invoke(): Flow<List<County>> {
        return countyRepository.getCounties()
            .onStart {
                // ensures counties are available
                highwayRepository.getHighwayInfo()
            }
    }
}