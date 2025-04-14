package com.szaniszlo.yettelhomeassignment.domain.repository

import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import kotlinx.coroutines.flow.Flow

interface CountyRepository {
    fun saveCounties(counties: List<County>)
    fun getCounties(): Flow<List<County>>
}