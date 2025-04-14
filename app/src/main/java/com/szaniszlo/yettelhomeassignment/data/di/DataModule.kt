package com.szaniszlo.yettelhomeassignment.data.di

import com.szaniszlo.yettelhomeassignment.data.repository.DefaultCountyRepository
import com.szaniszlo.yettelhomeassignment.data.repository.DefaultHighwayRepository
import com.szaniszlo.yettelhomeassignment.domain.repository.CountyRepository
import com.szaniszlo.yettelhomeassignment.domain.repository.HighwayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    internal abstract fun bindCountyRepository(
        repo: DefaultCountyRepository,
    ): CountyRepository

    @Binds
    internal abstract fun bindHighwayRepository(
        repo: DefaultHighwayRepository,
    ): HighwayRepository
}