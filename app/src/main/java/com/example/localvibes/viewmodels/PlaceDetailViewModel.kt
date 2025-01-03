package com.example.localvibes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.localvibes.viewstates.PlaceDetailViewState
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaceDetailViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(PlaceDetailViewState(""))
    val viewState : StateFlow<PlaceDetailViewState> = _viewState.asStateFlow()

    fun setPlaceId(placeId: String) {
        _viewState.update {
            it.copy(bookId = placeId)
        }
    }

    fun initLoad(placeId: String){
        setPlaceId(placeId)
        // Load from API
    }
}