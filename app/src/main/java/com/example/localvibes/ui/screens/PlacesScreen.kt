package com.example.localvibes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.localvibes.viewmodels.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(
    navController: NavController?,
    viewModel: PlacesViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Places Screen")
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Red
                )
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ){
            Text(
                "Statický text"
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item{
                    Text(
                        text = "Místa",
                        style = MaterialTheme.typography.titleLarge
                    )

                }

                items(viewState.value.places) { place ->
                    Card (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .clickable {
                                navController?.navigate("PlaceDetailScreen/$place")
                            }
                    ) {
                        Text(
                            text = place,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
        
    }
}

@Composable
@Preview
fun PlacesScreenPreview() {
    PlacesScreen(navController = null, viewModel = PlacesViewModel())
}