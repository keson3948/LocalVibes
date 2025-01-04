package com.example.localvibes.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localvibes.api.RetrofitInstance
import com.example.localvibes.repositories.PlaceRepository
import com.example.localvibes.viewstates.PlaceDetailViewState
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaceDetailViewModel : ViewModel() {
    private val PlaceRepository = PlaceRepository(RetrofitInstance.placeApi)

    private val _viewState = MutableStateFlow(PlaceDetailViewState(""))
    val viewState : StateFlow<PlaceDetailViewState> = _viewState.asStateFlow()

    fun setPlaceId(placeId: String) {
        _viewState.update {
            it.copy(placeId = placeId)
        }
    }

    fun initLoad(placeId: String) {
        setPlaceId(placeId)

        viewModelScope.launch {
            try {
                val place = PlaceRepository.getPlace(placeId)
                _viewState.update {
                    it.copy(place = place)
                }
                Log.d("PlaceDetailViewModel", "Place received: $place")
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Error fetching place: ${e.message}")
            }
        }
    }

    fun resetPlace() {
        _viewState.update {
            it.copy(place = null)
        }
    }
}