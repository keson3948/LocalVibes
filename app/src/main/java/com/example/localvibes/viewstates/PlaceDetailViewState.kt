package com.example.localvibes.viewstates

import com.example.localvibes.models.Place

data class PlaceDetailViewState(
    val placeId : String = "",
    val isLoading: Boolean = false,
    val place: Place? = null
)
