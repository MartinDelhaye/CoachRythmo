package com.example.coachrythmo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val name: String,
    val description: String,
    val category: String,
    val day: String,
    val startTime: String,
    val difficulty: String,
    val durationMinutes: Int?
)