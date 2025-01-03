package com.example.localvibes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlacesViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(PlacesViewState())
    val viewState : StateFlow<PlacesViewState> = _viewState.asStateFlow()

    init {
        _viewState.value = PlacesViewState(isLoading = true)
        fetchPlaces()
    }

    fun fetchPlaces() {
        val list = listOf("Place 1", "Place 2", "Place 3")
        _viewState.update {
            it.copy(places = list, isLoading = false)
        }
    }

}