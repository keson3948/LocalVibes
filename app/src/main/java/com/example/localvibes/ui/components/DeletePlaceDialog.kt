package com.example.localvibes.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeletePlaceDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Smazat místo")
        },
        text = {
            Text("Opravdu chcete smazat toto místo? Tato akce je nevratná.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Smazat")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Zrušit")
            }
        }
    )
}