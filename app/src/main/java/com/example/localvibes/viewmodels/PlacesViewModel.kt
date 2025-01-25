package com.example.localvibes.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localvibes.api.RetrofitInstance
import com.example.localvibes.repositories.CategoryRepository
import com.example.localvibes.repositories.PlaceRepository
import com.example.localvibes.utils.FavoritesManager
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlacesViewModel(application: Application) : AndroidViewModel(application) {
    private val PlaceRepository = PlaceRepository(RetrofitInstance.placeApi)
    private val CategoryRepository = CategoryRepository(RetrofitInstance.placeApi)
    private val favoritesManager = FavoritesManager(application)

    private val _viewState = MutableStateFlow(PlacesViewState())
    val viewState : StateFlow<PlacesViewState> = _viewState.asStateFlow()
    val isLoading = mutableStateOf(false)

    init {
        _viewState.value = PlacesViewState(isLoading = true)
        //fetchPlaces()
        getPlacesFromApi()
    }

    fun getPlacesFromApi() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val places = PlaceRepository.getPlaces()
                val categories = CategoryRepository.getCategories()
                _viewState.update {
                    it.copy(places = places, isLoading = false, categories = categories)
                }
                Log.d("PlacesViewModel", "Places received: $places")
            } catch (e: Exception) {
                Log.e("PlacesViewModel", "Error fetching places: ${e.message}")
                _viewState.update {
                    it.copy(isLoading = false)
                }
            }
            finally {
                isLoading.value = false
            }
        }
    }

    fun getFavoritePlaces() {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val favoriteIds = favoritesManager.getFavorites()
                val favoritePlaces = PlaceRepository.getPlaces().filter { it.Id in favoriteIds }
                _viewState.update {
                    it.copy(places = favoritePlaces, isLoading = false)
                }
                Log.d("PlacesViewModel", "Favorite places received: $favoritePlaces")
            } catch (e: Exception) {
                Log.e("PlacesViewModel", "Error fetching favorite places: ${e.message}")
                _viewState.update {
                    it.copy(isLoading = false)
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onCategorySelected(categoryId: String) {
        _viewState.update {
            it.copy(selectedCategoryId = categoryId)
        }
        if(categoryId == ""){
            getPlacesFromApi()
        }
        else if (categoryId == "favorites") {
            getFavoritePlaces()
        }
        else if(viewState.value.search == ""){
            searchByCategory(categoryId)
        }
        else{
            searchByNameAndCategory()
        }
    }

    fun searchByCategory(categoryId: String){
        viewModelScope.launch {
            try {
                val places = PlaceRepository.searchByCategory(categoryId)
                _viewState.update {
                    it.copy(places = places)
                }
                Log.d("PlacesViewModel", "Places received: $places")
            } catch (e: Exception) {
                Log.e("PlacesViewModel", "Error fetching places: ${e.message}")
            }finally {
                isLoading.value = false
            }
        }
    }

    fun searchByNameAndCategory(){
        val query = viewState.value.search
        val categoryId = viewState.value.selectedCategoryId
        if(query == "" && categoryId == ""){
            getPlacesFromApi()
        }
        else if(query == ""){
            searchByCategory(categoryId)
        }
        else if(categoryId == ""){
            searchPlaces()
        }
        else{
            viewModelScope.launch {
                try {
                    val places = PlaceRepository.searchByNameAndCategory(query, categoryId)
                    Log.d("PlacesViewModel", "Places received: $places")
                    _viewState.update {
                        it.copy(places = places)
                    }
                    Log.d("PlacesViewModel", "Places received: $places")
                } catch (e: Exception) {
                    Log.e("PlacesViewModel", "Error fetching places: ${e.message}")
                }finally {
                    isLoading.value = false
                }
            }
        }
    }


    fun onSearchChange(query: String){
        _viewState.update {
            it.copy(search = query)
        }
        searchPlaces()
    }

    fun searchPlaces(){
        isLoading.value = true
        val query = viewState.value.search
        if(viewState.value.selectedCategoryId == "favorites"){
            if(query == ""){
                getFavoritePlaces()
            }
            else {
                viewModelScope.launch {
                    try {
                        val favoriteIds = favoritesManager.getFavorites()
                        val favoritePlaces =
                            PlaceRepository.searchPlaces(query).filter { it.Id in favoriteIds }
                        _viewState.update {
                            it.copy(places = favoritePlaces)
                        }
                        Log.d("PlacesViewModel", "Favorite places received: $favoritePlaces")
                    } catch (e: Exception) {
                        Log.e("PlacesViewModel", "Error fetching favorite places: ${e.message}")
                    } finally {
                        isLoading.value = false
                    }
                }
            }
        }
        else if(viewState.value.selectedCategoryId != ""){
            searchByNameAndCategory()
        }
        else if(query == ""){
            getPlacesFromApi()
        }
        else{
            viewModelScope.launch {
                try {
                    val places = PlaceRepository.searchPlaces(query)
                    _viewState.update {
                        it.copy(places = places)
                    }
                    Log.d("PlacesViewModel", "Places received: $places")
                } catch (e: Exception) {
                    Log.e("PlacesViewModel", "Error fetching places: ${e.message}")
                }
                finally {
                    isLoading.value = false
                }
            }
        }
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.SetIsResumed -> {
                _viewState.update { it.copy(isResumed = event.isResumed) }
            }
            MainScreenEvent.ResumeReload -> {
                getPlacesFromApi()
                _viewState.update { it.copy(isResumed = false, search = "", selectedCategoryId = "") }
            }
        }
    }

}