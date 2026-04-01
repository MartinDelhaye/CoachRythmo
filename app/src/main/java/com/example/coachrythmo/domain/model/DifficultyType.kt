package com.example.coachrythmo.domain.model

import androidx.compose.ui.graphics.Color
import com.example.coachrythmo.ui.theme.CREasyDifficulty
import com.example.coachrythmo.ui.theme.CRHighDifficultyColor
import com.example.coachrythmo.ui.theme.CRMediumDifficulty

enum class DifficultyType(
    val label: String,
    val color: Color,
    val level: Int
) {
    EASY("Facile", CREasyDifficulty, 1),
    MEDIUM("Moyen", CRMediumDifficulty, 2),
    HARD("Difficile", CRHighDifficultyColor, 3)
}