// File: app/src/main/java/com/example/localvibes/ui/screens/AddPlaceScreen.kt

package com.example.localvibes.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.localvibes.models.Category
import com.example.localvibes.models.Place
import com.example.localvibes.ui.components.NavigationBackButton
import com.example.localvibes.viewmodels.AddPlaceViewModel
import com.example.localvibes.viewmodels.PlaceDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(
    navController: NavController?,
    placeId: String?,
    viewModel: AddPlaceViewModel
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(placeId) {
        if(placeId != "") {
            if (placeId != null) {
                viewModel.setPlaceId(placeId)
                viewModel.loadPlaceForEdit(placeId)
            }
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Log.d("AddPlaceScreen", "PLACEID: ${viewState.placeId}")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (viewState.placeId == "") "Přidat místo" else "Upravit místo")
                },
                navigationIcon = {
                    NavigationBackButton(
                        onClick = {
                            navController?.popBackStack()
                            viewModel.resetPlace()
                        }
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = viewModel.snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = viewState.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = { Text("Název") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewState.description,
                onValueChange = {
                    viewModel.onDescriptionChanged(it)
                },
                label = { Text("Popis") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = viewState.selectedCategory?.Name ?: "Vyberte kategorii",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategorie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewState.categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category.Name,
                                )
                            },
                            onClick = {
                                viewModel.onCategorySelected(category)
                                expanded = false
                            }
                        )
                    }
                }
            }
            TextField(
                value = viewState.openingHours,
                onValueChange = { viewModel.onOpeningHoursChanged(it) },
                label = { Text("Otevírací doba") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewState.address,
                onValueChange = { viewModel.onAddressChanged(it) },
                label = { Text("Adresa") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = viewState.imageUrl,
                onValueChange = { viewModel.onImageUrlChanged(it) },
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                if (viewState.placeId == "") {
                    viewModel.validateAndAddPlace(
                        name = viewState.name,
                        description = viewState.description,
                        selectedCategory = viewState.selectedCategory,
                        imageUrl = viewState.imageUrl,
                        openingHours = viewState.openingHours,
                        address = viewState.address,
                        onAddPlace = { newPlace ->
                            navController?.popBackStack()
                        }
                    )
                } else {
                    // Pokud upravujeme existující místo
                    viewModel.validateAndEditPlace(
                        id = viewState.placeId!!,
                        name = viewState.name,
                        description = viewState.description,
                        selectedCategory = viewState.selectedCategory,
                        imageUrl = viewState.imageUrl,
                        openingHours = viewState.openingHours,
                        address = viewState.address,
                        onEditPlace = { Place ->
                            navController?.popBackStack()
                        }
                    )
                }
            }) {
                Text(if (viewState.placeId == "") "Přidat místo" else "Upravit místo")
            }

        }
    }
}