package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@Composable
fun RoutineCard(
    routine: RoutineVM,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CRPrimaryRed
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = routine.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                DifficultyBadge(routine.difficulty)
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Catégorie
            Text(
                text = routine.category,
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Jour + Heure
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${routine.day} • ${routine.startTime}",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}