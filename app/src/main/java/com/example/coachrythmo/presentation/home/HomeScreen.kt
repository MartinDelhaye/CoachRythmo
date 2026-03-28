package com.example.coachrythmo.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen

@Composable
fun HomeScreen(
    navController: NavController
) {
    AppScreen(
        navController = navController,
        title="Accueil"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
}