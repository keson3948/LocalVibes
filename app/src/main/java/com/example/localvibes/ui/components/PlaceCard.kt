// File: app/src/main/java/com/example/localvibes/ui/components/PlaceCard.kt

package com.example.localvibes.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.localvibes.models.Place
import com.example.localvibes.ui.screens.DashedLine

@SuppressLint("DefaultLocale")
@Composable
fun PlaceCard(place: Place, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(place.Id) },
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.wrapContentHeight()) {
            Image(
                painter = rememberAsyncImagePainter(model = place.ImageUrl),
                contentDescription = "Placeholder",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), // Fixed height
                contentScale = ContentScale.Crop // Maintain aspect ratio
            )
            Text(
                text = place.Name,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = place.Description ?: "No description",
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            DashedLine()
            Row {
                Text(
                    text = "Hodnocení: ${String.format("%.1f", place.AverageRating)}",
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "|",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = place.Category.Name,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 16.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}