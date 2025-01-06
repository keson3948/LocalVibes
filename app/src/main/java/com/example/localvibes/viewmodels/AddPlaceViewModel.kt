package com.example.localvibes.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localvibes.api.RetrofitInstance
import com.example.localvibes.models.Place
import com.example.localvibes.repositories.CategoryRepository
import com.example.localvibes.repositories.PlaceRepository
import com.example.localvibes.viewstates.AddPlaceViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarDuration
import com.example.localvibes.models.Category
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AddPlaceViewModel : ViewModel() {
    private val placeRepository = PlaceRepository(RetrofitInstance.placeApi)
    private val categoryRepository = CategoryRepository(RetrofitInstance.placeApi)

    private val _viewState = MutableStateFlow(AddPlaceViewState())
    val viewState: StateFlow<AddPlaceViewState> = _viewState.asStateFlow()

    val snackbarHostState = SnackbarHostState()

    init {
        getCategoriesFromApi()
    }

    fun setPlaceId(placeId: String) {
        _viewState.update {
            it.copy(placeId = placeId)
        }
    }

    fun getCategoriesFromApi() {
        _viewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val categories = categoryRepository.getCategories()
                _viewState.update {
                    it.copy(categories = categories, isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("AddPlaceViewModel", "Error fetching categories: ${e.message}")
                _viewState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun loadPlaceForEdit(placeId: String) {
        viewModelScope.launch {
            try {
                _viewState.update { it.copy(isLoading = true) }
                val place = placeRepository.getPlace(placeId) // Implementujte metodu v repozitáři
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        name = place.Name,
                        description = place.Description,
                        selectedCategory = place.Category,
                        imageUrl = place.ImageUrl,
                        openingHours = place.OpeningHours,
                        address = place.Address
                    )
                }
            } catch (e: Exception) {
                Log.e("AddPlaceViewModel", "Error loading place: ${e.message}")
                _viewState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun validateAndAddPlace(
        name: String,
        description: String,
        selectedCategory: Category?,
        imageUrl: String,
        openingHours: String,
        address: String,
        onAddPlace: (Place) -> Unit
    ) {
        if (name.isBlank() || description.isBlank() || selectedCategory == null || imageUrl.isBlank() || openingHours.isBlank() || address.isBlank()) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Všechna pole musí být vyplněna")
            }
            return
        }

        val newPlace = Place(
            Id = "",
            Name = name,
            Description = description,
            Category = null,
            ImageUrl = imageUrl,
            AverageRating = null,
            OpeningHours = openingHours,
            Address = address,
            CategoryId = selectedCategory.Id
        )

        viewModelScope.launch {
            try {
                placeRepository.addPlace(newPlace)
                onAddPlace(newPlace)
                resetPlace()
            } catch (e: Exception) {
                Log.e("AddPlaceViewModel", "Error adding place: ${e.message}")
            }
        }
    }

    fun validateAndEditPlace(
        id: String,
        name: String,
        description: String,
        selectedCategory: Category?,
        imageUrl: String,
        openingHours: String,
        address: String,
        onEditPlace: (Place) -> Unit
    ) {
        if (name.isBlank() || description.isBlank() || selectedCategory == null || imageUrl.isBlank() || openingHours.isBlank() || address.isBlank()) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar("Všechna pole musí být vyplněna")
            }
            return
        }

        val updatedPlace = Place(
            Id = id,
            Name = name,
            Description = description,
            CategoryId = selectedCategory.Id,
            ImageUrl = imageUrl,
            OpeningHours = openingHours,
            Address = address,
            Category = null,
            AverageRating = null
        )

        viewModelScope.launch {
            try {
                placeRepository.updatePlace(updatedPlace)
                onEditPlace(updatedPlace)
                resetPlace()
            } catch (e: Exception) {
                Log.e("AddPlaceViewModel", "Error updating place: ${e.message}")
            }
        }
    }

    fun resetPlace() {
        _viewState.update {
            it.copy(
                name = "",
                description = "",
                selectedCategory = null,
                imageUrl = "",
                openingHours = "",
                address = "",
                placeId = ""
            )
        }
    }

    fun onNameChanged(newName: String) {
        _viewState.update { it.copy(name = newName) }
    }

    fun onDescriptionChanged(newDescription: String) {
        _viewState.update { it.copy(description = newDescription) }
    }

    fun onCategorySelected(newCategory: Category) {
        _viewState.update { it.copy(selectedCategory = newCategory) }
    }

    fun onImageUrlChanged(newImageUrl: String) {
        _viewState.update { it.copy(imageUrl = newImageUrl) }
    }

    fun onOpeningHoursChanged(newOpeningHours: String) {
        _viewState.update { it.copy(openingHours = newOpeningHours) }
    }

    fun onAddressChanged(newAddress: String) {
        _viewState.update { it.copy(address = newAddress) }
    }
}