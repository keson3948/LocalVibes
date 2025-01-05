package com.example.localvibes.models

data class Review(
    val id: String,
    val PlaceId: String,
    val ReviewerName: String,
    val Rating: Int,
    val ReviewText: String,
    val CreatedAt: String?
)
