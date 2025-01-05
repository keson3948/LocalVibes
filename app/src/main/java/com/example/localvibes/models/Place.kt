package com.example.localvibes.models

data class Place (
    val Id: String,
    val Name: String,
    val Address: String,
    val Description: String,
    val OpeningHours: String,
    val CategoryId: String,
    val ImageUrl: String,
    val Category: Category
)