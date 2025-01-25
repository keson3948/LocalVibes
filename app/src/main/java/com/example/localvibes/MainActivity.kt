package com.example.localvibes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.localvibes.ui.theme.LocalVibesTheme
import com.example.localvibes.viewmodels.AddPlaceViewModel
import com.example.localvibes.viewmodels.PlaceDetailViewModel
import com.example.localvibes.viewmodels.PlacesViewModel

class MainActivity : ComponentActivity() {
    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var placeDetailViewModel: PlaceDetailViewModel
    private lateinit var addPlaceViewModel: AddPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        placesViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        placeDetailViewModel = ViewModelProvider(this)[PlaceDetailViewModel::class.java]
        addPlaceViewModel = ViewModelProvider(this)[AddPlaceViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            MaterialTheme() {
                val navController = rememberNavController()
                AppNavGraph(navController, placesViewModel, placeDetailViewModel, addPlaceViewModel)
            }
        }
    }
}