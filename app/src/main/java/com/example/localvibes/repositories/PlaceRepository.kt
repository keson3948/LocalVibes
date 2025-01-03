package com.example.localvibes.repositories

import com.example.localvibes.api.PlaceApi
import com.example.localvibes.models.Place

class PlaceRepository(private val placeApi: PlaceApi) {
    suspend fun getPlaces(): List<String> {
        return placeApi.getPlaces()
    }
}