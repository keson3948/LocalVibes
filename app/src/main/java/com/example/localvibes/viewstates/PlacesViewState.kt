package com.example.localvibes.viewstates

import kotlinx.coroutines.flow.Flow

data class PlacesViewState(
    val isLoading: Boolean = false,
    val places: List<String> = emptyList(),

    val snackbarMessage: Flow<String>? = null,
    val search: String = ""
)
