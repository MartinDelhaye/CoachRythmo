package com.example.coachrythmo.data.seed

import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Session

object SessionSeed {
    fun getSessionsWithExercises(): List<SessionSeedData> {
        val now = System.currentTimeMillis()
        return listOf(
            SessionSeedData(
                session = Session(
                    name = "Push",
                    category = "Pectoraux / Triceps",
                    date = now - 2 * 86400000,
                    difficulty = Difficulty.EASY,
                    duration = 45L
                ),
                exerciseIds = listOf(1, 2)
            ),
            SessionSeedData(
                session = Session(
                    name = "Pull",
                    category = "Dos / Biceps",
                    date = now - 86400000,
                    difficulty = Difficulty.MEDIUM,
                    duration = 30L
                ),
                exerciseIds = listOf(3, 4)
            )
        )
    }
}


data class SessionSeedData(
    val session: Session,
    val exerciseIds: List<Int>
)