package com.example.coachrythmo.data.source

import androidx.room.TypeConverter
import com.example.coachrythmo.domain.model.DifficultyType

class Converters {

    @TypeConverter
    fun fromDifficulty(value: DifficultyType): String {
        return value.name
    }

    @TypeConverter
    fun toDifficulty(value: String): DifficultyType {
        return when (value) {
            "Facile" -> DifficultyType.EASY
            "Moyen" -> DifficultyType.MEDIUM
            "Difficile" -> DifficultyType.HARD
            else -> DifficultyType.EASY
        }
    }
}