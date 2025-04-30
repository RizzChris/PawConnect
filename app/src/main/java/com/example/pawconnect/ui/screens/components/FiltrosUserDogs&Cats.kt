package com.example.pawconnect.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FiltrosContent(
    onFiltrosAplicados: (String, String) -> Unit // Callback para aplicar filtros
) {
    var tamanoSeleccionado by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Filtro por tamaño
        Text(text = "Filtrar por Tamaño", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        RadioGroup(
            options = listOf("Chico", "Mediano", "Grande"),
            selectedOption = tamanoSeleccionado,
            onOptionSelected = { tamanoSeleccionado = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filtro por género
        Text(text = "Filtrar por Género", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        RadioGroup(
            options = listOf("Macho", "Hembra"),
            selectedOption = generoSeleccionado,
            onOptionSelected = { generoSeleccionado = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para aplicar filtros
        Button(
            onClick = {
                onFiltrosAplicados(tamanoSeleccionado, generoSeleccionado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Aplicar Filtros")
        }
    }
}




@Composable
fun RadioGroup(
    options: List<String>, // Lista de opciones (por ejemplo, ["Pequeño", "Mediano", "Grande"])
    selectedOption: String, // Opción seleccionada actualmente
    onOptionSelected: (String) -> Unit // Callback cuando se selecciona una opción
) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option) }
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option)
            }
        }
    }
}