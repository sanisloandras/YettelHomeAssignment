package com.szaniszlo.yettelhomeassignment.data.mapper

import com.szaniszlo.yettelhomeassignment.data.dto.HighwayInfoDto
import com.szaniszlo.yettelhomeassignment.domain.model.county.County

internal fun HighwayInfoDto.mapCounties() = payload.counties.map { countyDto ->
    val relatedHighwayVignette = payload
        .highwayVignettes
        .first { it.vignetteType.contains(countyDto.id) }

    County(
        id = countyDto.id,
        name = countyDto.name,
        cost = relatedHighwayVignette.cost,
        trxFee = relatedHighwayVignette.trxFee,
        sum = relatedHighwayVignette.sum,
    )
}