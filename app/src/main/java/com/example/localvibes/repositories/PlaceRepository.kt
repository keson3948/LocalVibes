package com.example.localvibes.repositories

import com.example.localvibes.api.PlaceApi
import com.example.localvibes.models.Place

class PlaceRepository(private val placeApi: PlaceApi) {

    suspend fun getPlaces(): List<Place> {
        return placeApi.getPlaces()
    }

    suspend fun getPlace(id: String): Place {
        return placeApi.getPlace(id)
    }

    suspend fun searchPlaces(query: String): List<Place> {
        return placeApi.searchPlaces(query)
    }

    suspend fun searchByCategory(categoryId: String): List<Place> {
        return placeApi.searchByCategory(categoryId)
    }

    suspend fun searchByNameAndCategory(query: String, categoryId: String): List<Place> {
        return placeApi.searchByNameAndCategory(categoryId,query)
    }

    suspend fun addPlace(place: Place): Place {
        return placeApi.addPlace(place)
    }

    suspend fun deletePlace(id: String) {
        return placeApi.deletePlace(id)
    }

    suspend fun updatePlace(place: Place) {
        return placeApi.updatePlace(place.Id, place)
    }
}