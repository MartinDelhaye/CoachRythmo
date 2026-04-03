package com.example.coachrythmo.domain.model

import androidx.compose.ui.graphics.Color

enum class Difficulty(
    val label: String,
    val color: Color
) {
    EASY("Facile", Color(0xFF4CAF50)),
    MEDIUM("Moyen", Color(0xFFFF9800)),
    HARD("Difficile", Color(0xFFF44336));

    companion object {
        fun fromLabel(label: String): Difficulty =
            entries.firstOrNull { it.label.equals(label, ignoreCase = true) } ?: EASY
    }
}