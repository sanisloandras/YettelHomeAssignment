package com.szaniszlo.yettelhomeassignment.data.repository

import com.szaniszlo.yettelhomeassignment.domain.model.county.County
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCountyRepository @Inject constructor() : CountyRepository {

    // basic in-memory cache, could be abstracted as a data source
    private val counties = MutableStateFlow(emptyList<County>())

    override fun saveCounties(counties: List<County>) {
        this.counties.update { counties }
    }

    override fun getCounties() = counties.asStateFlow()
}