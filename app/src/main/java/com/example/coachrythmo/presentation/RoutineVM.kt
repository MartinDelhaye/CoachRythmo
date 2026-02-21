package com.example.coachrythmo.presentation

import androidx.compose.ui.graphics.Color
import com.example.coachrythmo.ui.theme.CREasyDifficulty
import com.example.coachrythmo.ui.theme.CRHighDifficultyColor
import com.example.coachrythmo.ui.theme.CRMediumDifficulty
import kotlin.random.Random

data class RoutineVM(
    val id: Int = Random.nextInt(),
    val name: String,
    val description: String,
    val category: String,
    val day: String,
    val startTime: String,
    val difficulty: DifficultyType
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


private val routinesList = mutableListOf(
    RoutineVM(
        id = 1,
        name = "Push",
        description = "Description",
        category = "Pectoraux / Triceps",
        day = "Lundi",
        startTime = "20:00",
        difficulty = EasyDifficulty
    ),
    RoutineVM(
        id = 2,
        name = "Pull",
        description = "Description",
        category = "Dos / Biceps",
        day = "Mercredi",
        startTime = "18:30",
        difficulty = StandardDifficulty
    ),
    RoutineVM(
        id = 3,
        name = "Leg Day",
        description = "Description",
        category = "Jambes",
        day = "Vendredi",
        startTime = "19:00",
        difficulty = HighDifficulty
    ),
    RoutineVM(
        id = 4,
        name = "Cardio",
        description = "Description",
        category = "Endurance",
        day = "Dimanche",
        startTime = "10:00",
        difficulty = StandardDifficulty
    )
)

fun getRoutines() : List<RoutineVM>
{
    return routinesList
}