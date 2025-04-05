@file:OptIn(MapsComposeExperimentalApi::class)

package com.szaniszlo.yettelhomeassignment.ui.components.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Polygon
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.domain.model.map.GeoJson
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelGreen

/**
 * Contains 2 implementations of country map. 1 uses GeoJSON utility. 1 uses custom polygon drawing.
 *
 * GeoJSON Standard: https://geojson.org/
 * GeoJSON Utility: https://developers.google.com/maps/documentation/android-sdk/utility/geojson
 * GeoJSON source: https://github.com/blackmad/neighborhoods/blob/master/hungary.geojson
 *
 * The GeoJSON file contains more then 100_000 lines.
 * It is too detailed for this feature, as we do not need to zoom in and display country borders
 * that precisely.
 *
 * Using ChatGPT generated a lighter version of Hungary's GeoJSON: hungary_simplified.geojson
 *
 * Currently the GeoJSON utility implementation parses the geojson file on the main thread,
 * which should be refactored.
 * The Google Maps compose does not support GeoJSON utility by default, only using MapEffect.
 */
@Composable
fun CountryMapCompose(
    modifier: Modifier = Modifier,
    geoJson: GeoJson?,
    selectedCounties: Set<String>,
    onClickCounty: (String) -> Unit,
    onMapLoaded: (() -> Unit)? = null,
    cameraPositionState: CameraPositionState,
    countryBounds: LatLngBounds,
) {
    val context = LocalContext.current

    var isMapLoaded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                onMapLoaded?.invoke()
                isMapLoaded = true
            },
            googleMapOptionsFactory = {
                // todo hardcoded color, does not support dark mode
                GoogleMapOptions().backgroundColor(context.getColor(R.color.white))
            },
            properties = remember {
                MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
                )
            },
            uiSettings = remember {
                MapUiSettings(
                    scrollGesturesEnabled = false, // Disable panning
                    zoomControlsEnabled = false,  // Disable pinch-to-zoom
                    zoomGesturesEnabled = false,  // Disable zoom buttons
                    rotationGesturesEnabled = false, // Disable rotation
                    tiltGesturesEnabled = false, scrollGesturesEnabledDuringRotateOrZoom = false
                )
            },
        ) {
            MoveCameraToCountryBounds(cameraPositionState, countryBounds)
            AddGeoJsonPolygons(geoJson, selectedCounties, onClickCounty)
        }

        AnimatedVisibility(
            visible = !isMapLoaded,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(Color.White)
                    .wrapContentSize()
            )
        }
    }
}

@Composable
private fun MoveCameraToCountryBounds(
    cameraPositionState: CameraPositionState, countryBounds: LatLngBounds
) {
    MapEffect(Unit) { map ->
        cameraPositionState.move(
            update = CameraUpdateFactory.newLatLngBounds(
                countryBounds, 0 // padding in pixels
            )
        )
    }
}

@Composable
private fun AddGeoJsonPolygons(
    geoJson: GeoJson?,
    selectedCounties: Set<String>,
    onClickCounty: (String) -> Unit
) {
    val selectedFillColor = yettelGreen
    val regularFillColor = Color(0xFFC4DFE9)
    geoJson?.features?.forEach { feature ->
        when (feature.geometry.type) {
            "Polygon" -> {
                Polygon(
                    // todo this mapping could be moved to a background thread
                    points = feature.geometry.coordinates[0].map {
                        LatLng(it[1], it[0])
                    },
                    fillColor = if (selectedCounties.contains(feature.properties.name)) {
                        selectedFillColor
                    } else {
                        regularFillColor
                    },
                    // todo hardcoded color, does not support dark mode
                    strokeColor = Color.White,
                    strokeWidth = 3f,
                    clickable = true,
                    onClick = {
                        onClickCounty(feature.properties.name)
                    }
                )
            }
        }
    }
}

/**
 * Native implementation using GeoJSON utility.
 */
/*
@Composable
private fun AddGeoJsonPolygons2(
    selectedCounties: Set<String>,
    onClickCounty: (String) -> Unit
) {
    val context = LocalContext.current

    MapEffect(Unit) { map ->
        val layer = GeoJsonLayer(map, R.raw.hungary, context)

        // todo instead of re-setting the whole layer, modify only the selected layer
        layer.removeLayerFromMap()

        layer.features.forEach {
            layer.setOnFeatureClickListener { feature ->
                Timber.d("onClickCounty ${feature.getProperty("name")}")
                onClickCounty(feature.getProperty("name"))
                layer.removeFeature(it)
                layer.addFeature(it)
            }
            it.polygonStyle = GeoJsonPolygonStyle().apply {
                strokeWidth = 3f
                // todo hardcoded color, does not support dark mode
                strokeColor = (context.getColor(R.color.white))
                fillColor = if (selectedCounties.contains(it.getProperty("name"))) {
                    context.getColor(R.color.yettel_green)
                } else {
                    context.getColor(R.color.regular_grey)
                }
                isClickable = true
            }
        }

        layer.addLayerToMap()
    }
}*/
