package com.example.coachrythmo.domain.model

import androidx.room.Entity

@Entity(
    tableName = "session_exercises",
    primaryKeys = ["sessionId", "exerciseId"]
)
data class SessionExercise(
    val sessionId: Int,
    val exerciseId: Int,
    val isDone: Boolean = false
)