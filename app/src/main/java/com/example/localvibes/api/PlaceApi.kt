package com.example.localvibes.api

import com.example.localvibes.models.Place
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PlaceApi {
    @GET("Place")
    suspend fun getPlaces(): List<Place>

    @GET("places/search")
    suspend fun searchPlaces(
        @Query("q") query: Query
    ): List<Place>

    @POST("places")
    suspend fun createPlace(
        @Body place: Place
    ): Place
}