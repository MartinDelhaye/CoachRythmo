package com.example.coachrythmo.data.source

import androidx.room.TypeConverter
import com.example.coachrythmo.domain.model.Difficulty

class Converters {

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty =
        runCatching { Difficulty.valueOf(value) }.getOrElse {
            Difficulty.fromLabel(value)
        }
}