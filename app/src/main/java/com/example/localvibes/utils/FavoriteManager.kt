package com.example.localvibes.utils

import android.content.Context
import android.content.SharedPreferences

class FavoritesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

    fun addFavorite(placeId: String) {
        val editor = sharedPreferences.edit()
        val favorites = getFavorites().toMutableSet()
        favorites.add(placeId)
        editor.putStringSet("favorites", favorites)
        editor.apply()
    }

    fun removeFavorite(placeId: String) {
        val editor = sharedPreferences.edit()
        val favorites = getFavorites().toMutableSet()
        favorites.remove(placeId)
        editor.putStringSet("favorites", favorites)
        editor.apply()
    }

    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
    }

    fun isFavorite(placeId: String): Boolean {
        return getFavorites().contains(placeId)
    }
}