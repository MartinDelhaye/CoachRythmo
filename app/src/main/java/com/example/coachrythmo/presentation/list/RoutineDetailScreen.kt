package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.DifficultyBadge
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import com.example.coachrythmo.ui.theme.CRWhite

@Composable
fun RoutineDetailScreen(
    navController: NavController,
    viewModel: ListRoutinesViewsModel,
    routineId: Int
) {
    val routine = viewModel.routines.value.firstOrNull { it.id == routineId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CRWhite)
    ) {
        // Header rouge avec back + nom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CRPrimaryRed)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.White
                )
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = routine.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                DifficultyBadge(routine.difficulty)
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {

            // Infos de la routine
            Text(
                text = "Informations",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(label = "Catégorie", value = routine.category)
            InfoRow(label = "Jour", value = routine.day)
            InfoRow(label = "Heure", value = routine.startTime)
            routine.durationMinutes?.let { InfoRow(label = "Durée", value = "$it min") }
            if (routine.description.isNotBlank()) {
                InfoRow(label = "Description", value = routine.description)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Section exercices (placeholder en attendant l'ajout par le coéquipier)
            Text(
                text = "Exercices",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF0F0), RoundedCornerShape(12.dp))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Les exercices seront disponibles prochainement",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
    }
    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
}
