package com.example.localvibes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.localvibes.models.Review

@Composable
fun AddReviewDialog(
    onDismiss: () -> Unit,
    onConfirm: (Review) -> Unit,
    existingReview: Review? = null
) {
    var name by remember { mutableStateOf(existingReview?.ReviewerName ?: "") }
    var reviewText by remember { mutableStateOf(existingReview?.ReviewText ?: "") }
    var rating by remember { mutableStateOf(existingReview?.Rating ?: 0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (existingReview == null) "Přidat recenzi" else "Upravit recenzi") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Jméno") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Recenze") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text("Hodnocení: ")
                    Slider(
                        value = rating.toFloat(),
                        onValueChange = { rating = it.toInt() },
                        valueRange = 0f..5f,
                        steps = 4
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(Review(existingReview?.Id ?: "", existingReview?.PlaceId ?: "", name, rating, reviewText, null))
                onDismiss()
            }) {
                Text(if (existingReview == null) "Přidat" else "Uložit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Zrušit")
            }
        }
    )
}