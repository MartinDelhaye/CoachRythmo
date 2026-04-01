package com.example.coachrythmo.presentation

import androidx.compose.ui.graphics.Color
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.ui.theme.CREasyDifficulty
import com.example.coachrythmo.ui.theme.CRHighDifficultyColor
import com.example.coachrythmo.ui.theme.CRMediumDifficulty

data class RoutineVM(
    val id: Int = -1,
    val name: String,
    val description: String = "",
    val category: String,
    val day: String,
    val startTime: String,
    val difficulty: DifficultyType,
    val durationMinutes: Int? = null
)

sealed class DifficultyType(
    val color: Color,
    val text: String
)

data object HighDifficulty : DifficultyType(
    CRHighDifficultyColor,
    "Difficile"
)

data object StandardDifficulty : DifficultyType(
    CRMediumDifficulty,
    "Moyen"
)

data object EasyDifficulty : DifficultyType(
    CREasyDifficulty,
    "Facile"
)

fun RoutineVM.toEntity(): Routine {
    return Routine(
        id = id,
        name = name,
        description = description,
        category = category,
        day = day,
        startTime = startTime,
        difficulty = difficulty.text, // on stock en String
        durationMinutes = durationMinutes
    )
}

fun Routine.toVM(): RoutineVM {
    return RoutineVM(
        name = name,
        description = description,
        category = category,
        day = day,
        startTime = startTime,
        difficulty = when (difficulty) {
            "Facile" -> EasyDifficulty
            "Moyen" -> StandardDifficulty
            "Difficile" -> HighDifficulty
            else -> EasyDifficulty
        },
        durationMinutes = durationMinutes
    )
}