package com.example.localvibes.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localvibes.api.RetrofitInstance
import com.example.localvibes.repositories.PlaceRepository
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {
    private val PlaceRepository = PlaceRepository(RetrofitInstance.placeApi)


    private val _viewState = MutableStateFlow(PlacesViewState())
    val viewState : StateFlow<PlacesViewState> = _viewState.asStateFlow()

    init {
        _viewState.value = PlacesViewState(isLoading = true)
        //fetchPlaces()
        getPlacesFromApi()
    }

    fun fetchPlaces() {
        val list = listOf("Place 1", "Place 2", "Place 3")
        //_viewState.update {
         //   it.copy(places = list, isLoading = false)
        //}
    }

    private fun getPlacesFromApi() {
        viewModelScope.launch {
            try {
                val places = PlaceRepository.getPlaces()
                _viewState.update {
                    it.copy(places = places, isLoading = false)
                }
                Log.d("PlacesViewModel", "Places received: $places")
            } catch (e: Exception) {
                Log.e("PlacesViewModel", "Error fetching places: ${e.message}")
                _viewState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }


    fun onSearchChange(query: String){
        _viewState.update {
            it.copy(search = query)
        }
    }

    fun searchPlaces(){

    }

}