package com.example.localvibes.repositories

import com.example.localvibes.api.PlaceApi
import com.example.localvibes.models.Review
import retrofit2.Response

class ReviewRepository (private val placeApi: PlaceApi) {

    suspend fun getReviews(placeId: String) : List<Review> {
        return placeApi.getReviews(placeId)
    }

    suspend fun addReview(review: Review) : Response<Review> {
        return placeApi.addReview(review)
    }

    suspend fun deleteReview(reviewId: String) {
        return placeApi.deleteReview(reviewId)
    }

    suspend fun updateReview(review: Review) {
        return placeApi.updateReview(review.Id, review)
    }

    /*



     */
}