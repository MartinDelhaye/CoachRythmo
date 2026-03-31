package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
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
fun RoutineCard(routine: RoutineVM, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F0)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder image
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = routine.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Points de difficulté
                DifficultyDots(routine.difficulty)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = routine.category,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Badge durée
            routine.durationMinutes?.let { duration ->
                Box(
                    modifier = Modifier
                        .background(CRPrimaryRed, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$duration", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "mins", color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}