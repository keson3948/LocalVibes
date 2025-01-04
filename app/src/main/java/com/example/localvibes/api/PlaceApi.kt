package com.example.localvibes.api

import com.example.localvibes.models.Category
import com.example.localvibes.models.Place
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceApi {
    @GET("Place")
    suspend fun getPlaces(): List<Place>

    @GET("Place/{id}")
    suspend fun getPlace(
        @Path("id") id: String
    ): Place

    @GET("Place/search-by-name")
    suspend fun searchPlaces(
        @Query("name") query: String
    ): List<Place>

    @POST("places")
    suspend fun createPlace(
        @Body place: Place
    ): Place

    @GET("Category")
    suspend fun getCategories(): List<Category>

    @GET("Place/ByCategory/{categoryId}")
    suspend fun searchByCategory(
        @Path("categoryId") categoryId: String
    ): List<Place>
}