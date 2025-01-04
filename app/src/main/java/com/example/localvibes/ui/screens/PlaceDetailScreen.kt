package com.example.localvibes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.localvibes.ui.components.NavigationBackButton
import com.example.localvibes.viewmodels.PlaceDetailViewModel
import com.example.localvibes.viewstates.PlaceDetailViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    placeDetailViewModel: PlaceDetailViewModel,
    navController: NavController?,
    placeId: String
) {

    LaunchedEffect(placeId) {
        placeDetailViewModel.initLoad(placeId)
    }

    val viewState = placeDetailViewModel.viewState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    viewState.place?.let { Text(text = it.Name) }
                },
                navigationIcon = {
                    NavigationBackButton(
                        onClick = {
                            placeDetailViewModel.resetPlace()
                            navController?.popBackStack()
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            viewState.place?.let { place ->
                Image(
                    painter = rememberAsyncImagePainter(place.ImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text(text = "Name: ${place.Name}")
                Text(text = "Category: ${place.Category.Name}")
                Text(text = "Location: ${place.Address}")
                // Add more fields as necessary
            }
        }
    }
}

@Composable
@Preview
fun PlaceDetailScreenPreview() {
    PlaceDetailScreen(PlaceDetailViewModel(), null, "1")
}