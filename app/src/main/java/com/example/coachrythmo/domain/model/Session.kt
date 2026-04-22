package com.example.coachrythmo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val category: String,
    val difficulty: Difficulty,
    val date: Long,
    val duration: Long
)