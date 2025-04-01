package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.model.highwayinfo.HighwayInfo
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetHighwayInfoUseCase @Inject constructor(
    private val highwayRepository: HighwayRepository,
) {

    suspend operator fun invoke(): HighwayInfo {
        return highwayRepository.getHighwayInfo()
    }
}