package com.example.localvibes.models

data class Review(
    val Id: String,
    val PlaceId: String,
    val ReviewerName: String,
    val Rating: Int,
    val ReviewText: String,
    val CreatedAt: String?
)
