package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.presentation.DifficultyType
import com.example.coachrythmo.presentation.EasyDifficulty
import com.example.coachrythmo.presentation.HighDifficulty
import com.example.coachrythmo.presentation.StandardDifficulty

@Composable
fun DifficultyDots(difficulty: DifficultyType) {
    val filledDots = when (difficulty) {
        is EasyDifficulty -> 1
        is StandardDifficulty -> 2
        is HighDifficulty -> 3
        else -> 1
    }
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = if (index < filledDots) difficulty.color else Color.LightGray,
                        shape = CircleShape
                    )
            )
        }
    }
}