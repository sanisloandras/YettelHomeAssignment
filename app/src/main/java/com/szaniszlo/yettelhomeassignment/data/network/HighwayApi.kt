package com.szaniszlo.yettelhomeassignment.data.network

import com.szaniszlo.yettelhomeassignment.data.dto.HighwayInfoDto
import com.szaniszlo.yettelhomeassignment.data.dto.HighwayOrdersDto
import com.szaniszlo.yettelhomeassignment.data.dto.ReceivedOrdersDto
import com.szaniszlo.yettelhomeassignment.data.dto.VehicleDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface HighwayApi {

    @GET("highway/info")
    suspend fun info(): Response<HighwayInfoDto>

    @GET("highway/vehicle")
    suspend fun vehicle(): Response<VehicleDto>

    @POST("highway/order")
    suspend fun order(@Body body: HighwayOrdersDto): Response<ReceivedOrdersDto>

    companion object {
        // subject to change
        const val BASE_URL = "http://192.168.0.65:8080/v1/"
    }
}