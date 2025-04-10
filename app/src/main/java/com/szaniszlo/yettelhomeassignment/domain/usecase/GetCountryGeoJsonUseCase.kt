package com.szaniszlo.yettelhomeassignment.domain.usecase

import android.content.Context
import com.google.gson.Gson
import com.szaniszlo.yettelhomeassignment.data.di.AppDispatcher
import com.szaniszlo.yettelhomeassignment.data.di.Dispatcher
import com.szaniszlo.yettelhomeassignment.domain.model.map.GeoJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCountryGeoJsonUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        loadGeoJson(context, "hungary_simplified.geojson")?.let { geoJson ->
            geoJson.copy(
                features = geoJson.features
                    // filter out Budapest Fovaros as it is not required for our feature
                    .filter { it.properties.description != "Fovaros" }
            )
        }
    }

    fun loadGeoJson(context: Context, fileName: String): GeoJson? {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Gson().fromJson(json, GeoJson::class.java)
    }
}