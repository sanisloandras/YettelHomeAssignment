package com.szaniszlo.yettelhomeassignment.data.di

import com.google.gson.Gson
import com.szaniszlo.yettelhomeassignment.BuildConfig
import com.szaniszlo.yettelhomeassignment.data.network.HighwayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Singleton
    @Provides
    fun provideHighwayApi(
        okHttpClient: OkHttpClient,
    ): HighwayApi {
        return Retrofit.Builder().apply {
            baseUrl(HighwayApi.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(Gson()))
            client(okHttpClient)
        }.build().create(HighwayApi::class.java)
    }
}