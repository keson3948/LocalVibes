package com.example.localvibes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.localvibes.viewstates.PlacesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlacesViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(PlacesViewState())
    val viewState : StateFlow<PlacesViewState> = _viewState.asStateFlow()



}