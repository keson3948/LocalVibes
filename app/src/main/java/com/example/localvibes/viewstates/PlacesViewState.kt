package com.example.localvibes.viewstates

import com.example.localvibes.models.Category
import com.example.localvibes.models.Place

data class PlacesViewState(
    val isLoading: Boolean = false,
    val places: List<Place> = emptyList(),
    val categories: List<Category> = emptyList(),

    val selectedCategoryId: String = "",
    val search: String = ""
)
