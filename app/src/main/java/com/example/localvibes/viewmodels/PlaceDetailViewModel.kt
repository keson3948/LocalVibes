package com.example.localvibes.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localvibes.api.RetrofitInstance
import com.example.localvibes.models.Review
import com.example.localvibes.repositories.PlaceRepository
import com.example.localvibes.repositories.ReviewRepository
import com.example.localvibes.viewstates.PlaceDetailViewState
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarDuration

class PlaceDetailViewModel : ViewModel() {
    private val PlaceRepository = PlaceRepository(RetrofitInstance.placeApi)
    private val ReviewRepository = ReviewRepository(RetrofitInstance.placeApi)


    val snackbarHostState = SnackbarHostState()

    private val _viewState = MutableStateFlow(PlaceDetailViewState(""))
    val viewState : StateFlow<PlaceDetailViewState> = _viewState.asStateFlow()

    val isDialogOpen = mutableStateOf(false)

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
                val reviews = ReviewRepository.getReviews(placeId)
                _viewState.update {
                    it.copy(place = place, reviews = reviews)
                }
                Log.d("PlaceDetailViewModel", "Place received: $place")
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Error fetching place: ${e.message}")
            }
        }
    }

    fun resetPlace() {
        _viewState.update {
            it.copy(place = null, reviews = emptyList())
        }
    }



    fun addReview(review: Review) {
        val reviewWithPlaceId = review.copy(PlaceId = _viewState.value.placeId)
        Log.d("PlaceDetailViewModel", "Adding review: $reviewWithPlaceId")
        viewModelScope.launch {
            try {
                val response = ReviewRepository.addReview(reviewWithPlaceId)
                if (response.isSuccessful) {
                    _viewState.update {
                        it.copy(reviews = listOf(reviewWithPlaceId) + it.reviews)
                    }
                    snackbarHostState.showSnackbar(
                        message = "Recenze byla úspěšně přidána",
                        duration = SnackbarDuration.Short
                    )
                } else {
                    Log.e("PlaceDetailViewModel", "Error adding review: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Exception adding review: ${e.message}")
            }
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            try {
                ReviewRepository.deleteReview(review.Id)
                _viewState.update {
                    it.copy(reviews = it.reviews.filterNot { it.Id == review.Id })
                }
                snackbarHostState.showSnackbar(
                    message = "Recenze byla úspěšně smazána",
                    duration = SnackbarDuration.Short
                )
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Exception deleting review: ${e.message}")
            }
        }
    }

    val isDeleteDialogOpen = mutableStateOf(false)
    var reviewToDelete: Review? = null

    fun showDeleteDialog(review: Review) {
        reviewToDelete = review
        isDeleteDialogOpen.value = true
    }

    fun confirmDeleteReview() {
        reviewToDelete?.let { deleteReview(it) }
        isDeleteDialogOpen.value = false
    }

    fun dismissDeleteDialog() {
        reviewToDelete = null
        isDeleteDialogOpen.value = false
    }
}