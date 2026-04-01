package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.domain.model.DifficultyType

@Composable
fun DifficultyDots(difficulty: DifficultyType) {

    val filledDots = difficulty.level

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = if (index < filledDots) {
                            difficulty.color
                        } else {
                            Color.LightGray
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}