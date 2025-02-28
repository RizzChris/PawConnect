package com.example.pawconnect.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pawconnect.R
import com.example.pawconnect.repository.PetData


@Composable
fun PetCard(pet: PetData, onClickInfo: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD7DCE2))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val painter = rememberAsyncImagePainter(
                model = pet.petPhoto,
                fallback = painterResource(R.drawable.my_placeholder),
                error = painterResource(R.drawable.my_placeholder)
            )
            Image(
                painter = painter,
                contentDescription = pet.petName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pet.petName, style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                if (pet.petSex.isNotBlank()) {
                    Chip(text = pet.petSex.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                }
                if (pet.petAge.isNotBlank()) {
                    Chip(text = "${pet.petAge} años")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onClickInfo) {
                Text("Conóceme")
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFFFD966),
        modifier = Modifier
            .wrapContentWidth()
            .height(28.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}