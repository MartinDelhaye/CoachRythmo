package com.example.coachrythmo.data.seed

import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Routine

object RoutineSeed {

    fun getRoutines(): List<Routine> {
        return listOf(
            Routine(
                name = "Push",
                description = "Pecs / triceps",
                category = "Pectoraux / Triceps",
                day = "Lundi",
                startTime = "20:00",
                difficulty = Difficulty.EASY,
                durationMinutes = 45
            ),
            Routine(
                name = "Pull",
                description = "Dos / biceps",
                category = "Dos / Biceps",
                day = "Jeudi",
                startTime = "18:30",
                difficulty = Difficulty.MEDIUM,
                durationMinutes = 30
            ),
            Routine(
                name = "Leg Day",
                description = "Jambes complètes",
                category = "Jambes",
                day = "Vendredi",
                startTime = "19:00",
                difficulty = Difficulty.HARD,
                durationMinutes = 60
            )
        )
    }
}