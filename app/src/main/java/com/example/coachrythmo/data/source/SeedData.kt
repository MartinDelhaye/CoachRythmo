package com.example.coachrythmo.data.source

import com.example.coachrythmo.domain.model.*

object SeedData {

    fun getRoutines(): List<Routine> {
        return listOf(
            Routine(
                name = "Push",
                description = "Description",
                category = "Pectoraux / Triceps",
                day = "Lundi",
                startTime = "20:00",
                difficulty = "Facile",
                durationMinutes = 45
            ),
            Routine(
                name = "Pull",
                description = "Description",
                category = "Dos / Biceps",
                day = "Mercredi",
                startTime = "18:30",
                difficulty = "Moyen",
                durationMinutes = 30
            )
        )
    }
}