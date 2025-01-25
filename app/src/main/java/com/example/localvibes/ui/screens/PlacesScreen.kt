package com.example.localvibes.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.localvibes.ui.components.NotFoundTextIcon
import com.example.localvibes.ui.components.PlaceCard
import com.example.localvibes.viewmodels.AddPlaceViewModel
import com.example.localvibes.viewmodels.MainScreenEvent
import com.example.localvibes.viewmodels.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(
    navController: NavController?,
    viewModel: PlacesViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    val focusManager = LocalFocusManager.current
    val isLoading by viewModel.isLoading

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onEvent(MainScreenEvent.SetIsResumed(true))
        }
    }
    if (viewState.value.isResumed) {
        viewModel.onEvent(MainScreenEvent.ResumeReload)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Local Vibes")
                },
                colors = TopAppBarDefaults.topAppBarColors().copy()
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController?.navigate("AddPlaceScreen/")  }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewState.value.search,
                    onValueChange = {
                        viewModel.onSearchChange(it)
                    },
                    placeholder = { Text(text = "Hledat...") },
                    modifier = Modifier
                        .weight(1f)

                        .height(56.dp), // Výška pole
                    shape = RoundedCornerShape(24.dp), // Zaoblené rohy
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.searchPlaces()
                            focusManager.clearFocus()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5), // Světle šedé pozadí
                        focusedIndicatorColor = Color.Transparent, // Skrytí čáry při zaměření
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )



                IconButton(
                    onClick = {
                        viewModel.searchPlaces()
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryButton(
                        text = "Vše",
                        isSelected = viewState.value.selectedCategoryId == "",
                        onClick = { viewModel.onCategorySelected("") }
                    )
                }
                item {
                    CategoryButton(
                        text = "Oblíbené",
                        isSelected = viewState.value.selectedCategoryId == "favorites",
                        onClick = { viewModel.onCategorySelected("favorites") }
                    )
                }
                items(viewState.value.categories) { category ->
                    CategoryButton(
                        text = category.Name,
                        isSelected = viewState.value.selectedCategoryId == category.Id,
                        onClick = { viewModel.onCategorySelected(category.Id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Přidání mezery

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (viewState.value.places.isEmpty()) {
                        item {
                            NotFoundTextIcon("Nebyly nalezeny žádné výsledky")
                        }
                    } else {
                        items(viewState.value.places) { place ->
                            PlaceCard (place = place) { placeId ->
                                navController?.navigate("PlaceDetailScreen/$placeId")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier.height(32.dp),
        border = BorderStroke(
            width = 1.dp,
            color =  Color(0xFF9D4EDD),
        ),
        colors = if (isSelected) {
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFF9D4EDD),

                contentColor = Color.White
            )
        } else {
            ButtonDefaults.outlinedButtonColors()
        }
    ) {
        Text(text = text)
    }
}

@Composable
fun DashedLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(10f, 10f),
                0f
            ) // Délka čárky a mezera
        )
    }
}

/*
@Composable
@Preview
fun PlacesScreenPreview() {
    PlacesScreen(navController = null, viewModel = PlacesViewModel())
}
 */