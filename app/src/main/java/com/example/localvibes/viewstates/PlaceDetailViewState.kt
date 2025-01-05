package com.example.localvibes.viewstates

import androidx.compose.runtime.mutableStateOf
import com.example.localvibes.models.Place
import com.example.localvibes.models.Review

data class PlaceDetailViewState(
    val placeId : String = "",
    val isLoading: Boolean = false,
    val place: Place? = null,
    val reviews: List<Review> = emptyList(),

)
