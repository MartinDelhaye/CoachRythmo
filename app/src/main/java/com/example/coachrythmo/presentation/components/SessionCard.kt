package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@Composable
fun SessionCard(
    name: String,
    category: String,
    difficulty: Difficulty,
    dateText: String,
    done: Int,
    total: Int
) {

    val progress = if (total == 0) 0f else done.toFloat() / total

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F0)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // 🔹 Ligne du haut
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                // 🔥 Badge progression
                Box(
                    modifier = Modifier
                        .background(CRPrimaryRed, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$done / $total",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Difficulté (réutilise ton composant)
            DifficultyDots(difficulty)

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Date
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}