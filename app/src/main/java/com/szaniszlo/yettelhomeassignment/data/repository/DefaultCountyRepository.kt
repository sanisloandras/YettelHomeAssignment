package com.szaniszlo.yettelhomeassignment.data.repository

import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCountyRepository @Inject constructor() : CountyRepository {

    override fun saveCounties(counties: List<County>) {
        TODO("Not yet implemented")
    }

    override fun getCounties(): Flow<List<County>> {
        TODO("Not yet implemented")
    }

}