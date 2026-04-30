package com.example.coachrythmo.presentation

import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Routine

data class RoutineVM(
    val id: Int? = null,
    val name: String,
    val description: String = "",
    val category: String,
    val day: String,
    val startTime: String,
    val difficulty: Difficulty,
    val durationMinutes: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

fun RoutineVM.toEntity(): Routine = Routine(
    id = id,
    name = name,
    description = description,
    category = category,
    day = day,
    startTime = startTime,
    difficulty = difficulty,
    durationMinutes = durationMinutes,
    latitude = latitude,
    longitude = longitude
)

fun Routine.toVM(): RoutineVM = RoutineVM(
    id = id,
    name = name,
    description = description,
    category = category,
    day = day,
    startTime = startTime,
    difficulty = difficulty,
    durationMinutes = durationMinutes,
    latitude = latitude,
    longitude = longitude
)