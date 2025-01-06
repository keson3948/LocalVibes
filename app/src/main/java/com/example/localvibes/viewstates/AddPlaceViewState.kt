package com.example.localvibes.viewstates

import com.example.localvibes.models.Category

data class AddPlaceViewState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val name: String = "",
    val description: String = "",
    val selectedCategory: Category? = null,
    val imageUrl: String = "",
    val openingHours: String = "",
    val address: String = "",
    val placeId: String? = ""
)
