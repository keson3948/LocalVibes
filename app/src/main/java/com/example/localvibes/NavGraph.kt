package com.example.localvibes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.localvibes.ui.screens.PlacesScreen
import com.example.localvibes.viewmodels.PlacesViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    placesViewModel: PlacesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "PlacesScreen"
    ) {
        composable("PlacesScreen") {
            PlacesScreen(navController, placesViewModel)
        }
    }
}