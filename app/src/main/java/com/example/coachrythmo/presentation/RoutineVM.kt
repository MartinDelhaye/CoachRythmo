package com.example.coachrythmo.presentation

import com.example.coachrythmo.domain.model.DifficultyType
import com.example.coachrythmo.domain.model.Routine

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

fun RoutineVM.toEntity(): Routine {
    return Routine(
        id = id,
        name = name,
        description = description,
        category = category,
        day = day,
        startTime = startTime,
        difficulty = difficulty,
        durationMinutes = durationMinutes
    )
}

fun Routine.toVM(): RoutineVM {
    return RoutineVM(
        id = id ?: -1,
        name = name,
        description = description,
        category = category,
        day = day,
        startTime = startTime,
        difficulty = difficulty,
        durationMinutes = durationMinutes
    )
}