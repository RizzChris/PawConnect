package com.example.pawconnect.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pawconnect.R
import com.example.pawconnect.repository.PetData

@Composable
fun PetCard(pet: PetData, onClickInfo: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background) // Fondo adaptativo al tema
            .padding(8.dp) // Espaciado entre tarjetas
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer) // Fondo adaptativo de la Card
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
                Text(
                    text = pet.petName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface // Adaptado a modo oscuro
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (pet.petSex.isNotBlank()) {
                        Chip(text = pet.petSex.replaceFirstChar { it.titlecase() })
                    }
                    if (pet.petAge.isNotBlank()) {
                        Chip(text = "${pet.petAge} años")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onClickInfo,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Adaptado a modo oscuro
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Conóceme")
                }
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer, // Adaptado a modo oscuro
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
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer // Adaptado a modo oscuro
            )
        }
    }
}