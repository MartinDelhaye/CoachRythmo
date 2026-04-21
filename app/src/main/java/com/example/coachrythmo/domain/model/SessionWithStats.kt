package com.example.coachrythmo.domain.model

data class SessionWithStats(
    val id: Int,
    val name: String,
    val category: String,
    val difficulty: Difficulty,
    val date: Long,
    val duration: Long,
    val totalExercises: Int,
    val doneExercises: Int
)