package com.szaniszlo.yettelhomeassignment.ui.components.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.rememberCameraPositionState
import com.szaniszlo.yettelhomeassignment.domain.model.map.GeoJson

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CountryMapWrapper(
    geoJson: GeoJson?,
    selectedCounties: Set<String>,
    onClickCounty: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val aspectRatio = remember { getBoundsAspectRatio(HUNGARY_BOUNDS) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            BUDAPEST,
            // zoom level does not matter at this point
            0f
        )
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val mapWidthPx = constraints.maxWidth.toFloat()
        val density = LocalDensity.current
        val mapWidth = with(density) { mapWidthPx.toDp() }
        val mapHeight = with(density) { (mapWidthPx / aspectRatio).toDp() }

        CountryMapCompose(
            modifier = Modifier
                .width(mapWidth)
                .height(mapHeight),
            geoJson = geoJson,
            selectedCounties = selectedCounties,
            onClickCounty = onClickCounty,
            cameraPositionState = cameraPositionState,
            countryBounds = HUNGARY_BOUNDS
        )
    }
}

private fun getBoundsAspectRatio(bounds: LatLngBounds): Float {
    val height = SphericalUtil.computeDistanceBetween(
        LatLng(bounds.southwest.latitude, bounds.center.longitude),
        LatLng(bounds.northeast.latitude, bounds.center.longitude)
    )
    val width = SphericalUtil.computeDistanceBetween(
        LatLng(bounds.center.latitude, bounds.southwest.longitude),
        LatLng(bounds.center.latitude, bounds.northeast.longitude)
    )
    return (width / height).toFloat().coerceAtLeast(0.01f) // safe minimum
}

private val BUDAPEST = LatLng(47.497913, 19.040236)

private val HUNGARY_BOUNDS = LatLngBounds.builder()
    .include(LatLng(48.602913, 16.045671)) // top left
    .include(LatLng(45.752305, 22.998301)) // bottom right
    .build()