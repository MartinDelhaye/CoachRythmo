package com.example.coachrythmo.domain.model

import androidx.room.Entity

@Entity(
    tableName = "routine_exercise",
    primaryKeys = ["routineId", "exerciseId"]
)
data class RoutineExerciseCrossRef(
    val routineId: Int,
    val exerciseId: Int
)