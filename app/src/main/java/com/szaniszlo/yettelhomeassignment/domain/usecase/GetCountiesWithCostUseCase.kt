package com.szaniszlo.yettelhomeassignment.domain.usecase

import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import javax.inject.Inject

class GetCountiesWithCostUseCase @Inject constructor(
    private val countyRepository: CountyRepository,
) {

    operator fun invoke() = countyRepository.getCounties()
}