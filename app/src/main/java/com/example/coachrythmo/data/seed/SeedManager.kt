package com.example.coachrythmo.data.seed

import com.example.coachrythmo.data.source.*
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef
import com.example.coachrythmo.domain.model.SessionExercise

object SeedManager {

    suspend fun seedDatabase(
        routineDao: RoutineDao,
        exerciseDao: ExerciseDao,
        crossRefDao: RoutineExerciseDao,
        sessionDao: SessionDao
    ) {

        // Clear de la bdd
        sessionDao.clearSessionExercises()
        sessionDao.clearSessions()
        crossRefDao.clear()
        routineDao.clear()
        exerciseDao.clear()

        // Exercises
        val exercises = ExerciseSeed.getExercises()
        exerciseDao.insertAll(exercises)
        val insertedExercises = exerciseDao.getAllNow()

        // Routines
        val routines = RoutineSeed.getRoutines()
        routineDao.insertAll(routines)
        val insertedRoutines = routineDao.getAll()

        val push = insertedRoutines.first { it.name == "Push" }
        val pull = insertedRoutines.first { it.name == "Pull" }
        val legs = insertedRoutines.first { it.name == "Leg Day" }

        val crossRefs = listOf(
            RoutineExerciseCrossRef(push.id!!, insertedExercises.first { it.name == "Développé couché" }.id!!),
            RoutineExerciseCrossRef(push.id, insertedExercises.first { it.name == "Pompes" }.id!!),

            RoutineExerciseCrossRef(pull.id!!, insertedExercises.first { it.name == "Tractions" }.id!!),
            RoutineExerciseCrossRef(pull.id, insertedExercises.first { it.name == "Curl biceps" }.id!!),

            RoutineExerciseCrossRef(legs.id!!, insertedExercises.first { it.name == "Squats" }.id!!),
            RoutineExerciseCrossRef(legs.id, insertedExercises.first { it.name == "Fentes" }.id!!)
        )

        crossRefDao.insertAll(crossRefs)

        // SESSIONS

        val sessionSeeds = SessionSeed.getSessionsWithExercises()

        val sessionIds = sessionSeeds.map { seed ->
            sessionDao.insertSession(seed.session)
        }

        val sessionExercises = sessionSeeds.flatMapIndexed { index, seed ->
            val sessionId = sessionIds[index].toInt()

            seed.exerciseIds.map { exerciseId ->
                SessionExercise(
                    sessionId = sessionId,
                    exerciseId = exerciseId,
                    isDone = index % 2 == 0
                )
            }
        }
        sessionDao.insertSessionExercises(sessionExercises)
    }
}