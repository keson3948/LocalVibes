package com.example.localvibes.viewstates

data class PlacesViewState(
    val isLoading: Boolean = false,
    val places: List<String> = emptyList()
)
