package com.example.localvibes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.localvibes.ui.screens.AddPlaceScreen
import com.example.localvibes.ui.screens.PlaceDetailScreen
import com.example.localvibes.ui.screens.PlacesScreen
import com.example.localvibes.viewmodels.AddPlaceViewModel
import com.example.localvibes.viewmodels.PlaceDetailViewModel
import com.example.localvibes.viewmodels.PlacesViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    placesViewModel: PlacesViewModel,
    placeDetailViewModel: PlaceDetailViewModel,
    addPlaceViewModel: AddPlaceViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "PlacesScreen"
    ) {
        composable("PlacesScreen") {
            PlacesScreen(navController, placesViewModel)
        }

        composable("PlaceDetailScreen/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: ""
            PlaceDetailScreen(placeDetailViewModel, navController, placeId)
        }

        composable(
            route = "AddPlaceScreen/{placeId}",
            arguments = listOf(navArgument("placeId") { nullable = true })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")
            AddPlaceScreen(
                navController = navController,
                placeId = placeId,
                viewModel = addPlaceViewModel
            )
        }
    }
}