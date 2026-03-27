package com.example.coachrythmo.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Accueil - H1",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "H2",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Intégration à faire",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}