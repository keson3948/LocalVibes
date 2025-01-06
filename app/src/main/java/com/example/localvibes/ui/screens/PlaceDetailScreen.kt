package com.example.localvibes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Label
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.localvibes.models.Review
import com.example.localvibes.ui.components.AddReviewDialog
import com.example.localvibes.ui.components.DeletePlaceDialog
import com.example.localvibes.ui.components.DeleteReviewDialog
import com.example.localvibes.ui.components.NavigationBackButton
import com.example.localvibes.ui.components.NotFoundTextIcon
import com.example.localvibes.viewmodels.PlaceDetailViewModel

@SuppressLint("DefaultLocale")
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
    val isDialogOpen by placeDetailViewModel.isDialogOpen
    val isLoading by placeDetailViewModel.isLoading

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
                },
                actions = {
                    IconButton(onClick = {
                        navController?.navigate("addPlaceScreen/${placeId}")
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { placeDetailViewModel.showDeletePlaceDialog() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { placeDetailViewModel.isDialogOpen.value = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = placeDetailViewModel.snackbarHostState)
        }
    ) { innerPadding ->
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
                    .padding(innerPadding)
            ) {
                item {
                    viewState.place?.let { place ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(place.ImageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black),
                                            startY = 0f,
                                            endY = 400f
                                        )
                                    )
                            )
                            Text(
                                text = place.Name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.BottomStart)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Description Icon",
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Text(
                                            text = "O podniku",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = place.Description ?: "Žádný popis",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Rounded.Label,
                                            contentDescription = "Category Icon",
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Text(
                                            text = place.Category?.Name ?: "Žádná kategorie",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTimeFilled,
                                            contentDescription = "Time Icon",
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Text(
                                            text = place.OpeningHours,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Location Icon",
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                        Text(
                                            text = place.Address,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }

                    DashedLine()
                }

                item {
                    ReviewSection(
                        reviews = viewState.reviews,
                        onEditClick = { review ->
                            placeDetailViewModel.showEditDialog(review)
                        },
                        onDeleteClick = { review ->
                            placeDetailViewModel.showDeleteDialog(review)
                        }
                    )
                }
            }
        }

        if (isDialogOpen) {
            AddReviewDialog(
                onDismiss = { placeDetailViewModel.isDialogOpen.value = false },
                onConfirm = { review ->
                    placeDetailViewModel.addReview(review)
                }
            )
        }

        if (placeDetailViewModel.isEditDialogOpen.value) {
            AddReviewDialog(
                onDismiss = { placeDetailViewModel.dismissEditDialog() },
                onConfirm = { review ->
                    placeDetailViewModel.updateReview(review)
                },
                existingReview = placeDetailViewModel.reviewToEdit
            )
        }

        if (placeDetailViewModel.isDeleteDialogOpen.value) {
            DeleteReviewDialog(
                onDismiss = { placeDetailViewModel.dismissDeleteDialog() },
                onConfirm = { placeDetailViewModel.confirmDeleteReview() }
            )
        }

        if (placeDetailViewModel.isDeletePlaceDialogOpen.value) {
            DeletePlaceDialog(
                onDismiss = { placeDetailViewModel.dismissDeletePlaceDialog() },
                onConfirm = {
                    placeDetailViewModel.deletePlace(placeId) {
                        navController?.popBackStack()
                    }
                }
            )
        }

    }
}

@Composable
fun ReviewSection(
    reviews: List<Review>,
    onEditClick: (Review) -> Unit,
    onDeleteClick: (Review) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Recenze",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (reviews.isEmpty()) {
            NotFoundTextIcon("Žádné recenze")
        } else {
            reviews.forEach { review ->
                ReviewItem(
                    name = review.ReviewerName,
                    reviewText = review.ReviewText,
                    rating = review.Rating,
                    onEditClick = { onEditClick(review) },
                    onDeleteClick = { onDeleteClick(review) }
                )
            }
        }
    }
}

@Composable
fun ReviewItem(
    name: String,
    reviewText: String,
    rating: Int,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = reviewText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { index ->
                Icon(
                    imageVector = if (index < rating) Icons.Default.Star else Icons.Rounded.StarOutline,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
@Preview
fun PlaceDetailScreenPreview() {
    PlaceDetailScreen(PlaceDetailViewModel(), null, "1")
}