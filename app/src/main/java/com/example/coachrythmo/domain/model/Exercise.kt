package com.example.coachrythmo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val category: String
)