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
                difficulty = DifficultyType.EASY,
                durationMinutes = 45
            ),
            Routine(
                name = "Pull",
                description = "Description",
                category = "Dos / Biceps",
                day = "Mercredi",
                startTime = "18:30",
                difficulty = DifficultyType.MEDIUM,
                durationMinutes = 30
            ),
            Routine(
                name = "Leg Day",
                description = "Description",
                category = "Jambes",
                day = "Vendredi",
                startTime = "19:00",
                difficulty = DifficultyType.HARD,
                durationMinutes = 60

            ),
            Routine(
                name = "Cardio",
                description = "Description",
                category = "Endurance",
                day = "Dimanche",
                startTime = "10:00",
                difficulty = DifficultyType.MEDIUM,
                durationMinutes = 30
            )
        )
    }
}