package com.example.coachrythmo.data.seed

import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Session

object SessionSeed {

    fun getSessions(): List<Session> {

        val now = System.currentTimeMillis()

        return listOf(
            Session(
                name = "Push",
                category = "Pectoraux / Triceps",
                date = now,
                difficulty = Difficulty.EASY,
                duration = 45L

            ),
            Session(
                name = "Pull",
                category = "Dos / Biceps",
                date = now + 86400000, // +1 jour
                difficulty = Difficulty.MEDIUM,
                duration = 30L
            ),
            Session(
                name = "Leg Day",
                category = "Jambes",
                date = now + 2 * 86400000, // +2 jours
                difficulty = Difficulty.HARD,
                duration = 60L
            )
        )
    }
}
