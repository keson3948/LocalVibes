package com.example.localvibes.api

import com.example.localvibes.models.Category
import com.example.localvibes.models.Place
import com.example.localvibes.models.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("Place/byCategoryAndName")
    suspend fun searchByNameAndCategory(
        @Query("name") query: String,
        @Query("categoryId") categoryId: String
    ): List<Place>

    @GET("Review/place/{placeId}")
    suspend fun getReviews(
        @Path("placeId") placeId: String
    ): List<Review>

    @POST("Review")
    suspend fun addReview(
        @Body review: Review
    ) : Response<Review>

    @DELETE("Review/delete/{reviewId}")
    suspend fun deleteReview(
        @Path("reviewId") reviewId: String
    )

    @PUT("Review/{reviewId}")
    suspend fun updateReview(
        @Path("reviewId") reviewId: String,
        @Body review: Review
    )
}