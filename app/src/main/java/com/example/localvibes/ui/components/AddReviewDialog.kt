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
    onConfirm: (Review) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Review") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Review") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text("Rating: ")
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
                onConfirm(Review("", "", name, rating, reviewText, null))
                onDismiss()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}