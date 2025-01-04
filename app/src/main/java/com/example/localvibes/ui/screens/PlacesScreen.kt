package com.example.localvibes.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.localvibes.R
import com.example.localvibes.viewmodels.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(
    navController: NavController?,
    viewModel: PlacesViewModel
) {
    val viewState = viewModel.viewState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Local Vibes")
                },
                colors = TopAppBarDefaults.topAppBarColors().copy()
            )
        }
    ) { innerPadding ->
        Column (
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

            Spacer(modifier = Modifier.height(16.dp)) // Přidání mezery

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewState.value.places) { place ->
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController?.navigate("PlaceDetailScreen/${place.Id}")
                            },
                        elevation = CardDefaults.cardElevation(3.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ){
                        Column (
                            modifier = Modifier.wrapContentHeight()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.placehorder),
                                contentDescription = "Placeholder",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp), // Nastavení pevné výšky
                                contentScale = ContentScale.Crop // Zachování poměru stran
                            )
                            Text(
                                text = place.Name,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Description",
                                modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            DashedLine()


                            Text(
                                text = "Category",
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
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
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) // Délka čárky a mezera
        )
    }
}

@Composable
@Preview
fun PlacesScreenPreview() {
    PlacesScreen(navController = null, viewModel = PlacesViewModel())
}