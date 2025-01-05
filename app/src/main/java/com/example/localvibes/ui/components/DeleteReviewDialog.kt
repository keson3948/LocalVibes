package com.example.localvibes.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteReviewDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Opravdu vymazat recenzi?") },
        text = { Text(text = "Opravdu chcete tuto recenzi smazat?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Vymazat")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Zrušit")
            }
        }
    )
}