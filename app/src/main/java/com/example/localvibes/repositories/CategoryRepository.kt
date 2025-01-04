package com.example.localvibes.repositories

import com.example.localvibes.api.PlaceApi
import com.example.localvibes.models.Category

class CategoryRepository (private val placeApi: PlaceApi) {

        suspend fun getCategories(): List<Category> {
            return placeApi.getCategories()
        }
}