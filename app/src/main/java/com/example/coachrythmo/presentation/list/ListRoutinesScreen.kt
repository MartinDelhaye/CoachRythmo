package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.components.RoutineCard
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

@Composable
fun ListRoutinesScreen(navController: NavController, viewModel: ListRoutinesViewsModel) {
    val filters = listOf("Toutes", "Durée", "Catégorie", "Difficulté")
    var selectedFilter by remember { mutableStateOf("Toutes") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Routine", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(CRPrimaryRed, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Filtres
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filters) { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = CRPrimaryRed,
                        selectedLabelColor = Color.White,
                        containerColor = Color.Transparent,
                        labelColor = Color.Black
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = CRPrimaryRed,
                        selectedBorderColor = CRPrimaryRed
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton Ajouter
        Button(
            onClick = { navController.navigate(Screen.AddRoutineScreen.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CRPrimaryRed)
        ) {
            Text(text = "Ajouter une routine personnalisée", fontSize = 16.sp, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Liste
        LazyColumn {
            items(viewModel.routines.value) { routine ->
                RoutineCard(routine = routine)
            }
        }
    }
}