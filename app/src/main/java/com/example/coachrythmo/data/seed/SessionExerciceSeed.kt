package com.example.coachrythmo.data.seed

import com.example.coachrythmo.domain.model.SessionExercise

object SessionExerciseSeed {

    fun build(sessionIds: List<Long>): List<SessionExercise> {

        val exercises = mutableListOf<SessionExercise>()

        sessionIds.forEachIndexed { index, sessionId ->

            val id = sessionId.toInt()

            when (index) {
                0 -> exercises.addAll(
                    listOf(
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 1,
                            isDone = true
                        ),
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 2,
                            isDone = true
                        )
                    )
                )
                1 -> exercises.addAll(
                    listOf(
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 3,
                            isDone = true
                        ),
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 4,
                            isDone = false
                        )
                        )
                )
                2 -> exercises.addAll(
                    listOf(
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 5,
                            isDone = true
                        ),
                        SessionExercise(
                            sessionId = id,
                            exerciseId = 6,
                            isDone = true
                        )
                    )
                )
            }
        }

        return exercises
    }
}
