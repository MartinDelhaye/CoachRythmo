package com.example.coachrythmo.data.seed

import com.example.coachrythmo.data.repository.ExerciseRepository
import com.example.coachrythmo.data.source.*
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise

object SeedManager {
    suspend fun seedDatabase(
        routineDao: RoutineDao,
        exerciseDao: ExerciseDao,
        crossRefDao: RoutineExerciseDao,
        sessionDao: SessionDao
    ) {
        // Clear
        sessionDao.clearSessionExercises()
        sessionDao.clearSessions()
        crossRefDao.clear()
        routineDao.clear()
        exerciseDao.clear()

        // Exercices depuis l'API
        ExerciseRepository.fetchAndStore(exerciseDao)
        val insertedExercises = exerciseDao.getAllNow()

        // Routines
        val routines = RoutineSeed.getRoutines()
        routineDao.insertAll(routines)
        val insertedRoutines = routineDao.getAllNow()

        val push = insertedRoutines.firstOrNull { it.name == "Push" } ?: return
        val pull = insertedRoutines.firstOrNull { it.name == "Pull" } ?: return
        val legs = insertedRoutines.firstOrNull { it.name == "Leg Day" } ?: return

        val benchPress = insertedExercises.firstOrNull { it.name.contains("Bench press", ignoreCase = true) }
        val pushUp     = insertedExercises.firstOrNull { it.name.contains("Push-up", ignoreCase = true) }
        val pullUp     = insertedExercises.firstOrNull { it.name.contains("Pull-up", ignoreCase = true) }
        val curl       = insertedExercises.firstOrNull { it.name.contains("Curl", ignoreCase = true) }
        val squat      = insertedExercises.firstOrNull { it.name.contains("Squat", ignoreCase = true) }
        val lunge      = insertedExercises.firstOrNull { it.name.contains("Lunge", ignoreCase = true) }

        val crossRefs = listOfNotNull(
            benchPress?.let { RoutineExerciseCrossRef(push.id!!, it.id) },
            pushUp?.let     { RoutineExerciseCrossRef(push.id!!, it.id) },
            pullUp?.let     { RoutineExerciseCrossRef(pull.id!!, it.id) },
            curl?.let       { RoutineExerciseCrossRef(pull.id!!, it.id) },
            squat?.let      { RoutineExerciseCrossRef(legs.id!!, it.id) },
            lunge?.let      { RoutineExerciseCrossRef(legs.id!!, it.id) },
        )
        crossRefDao.insertAll(crossRefs)

        // Sessions de démo
        val firstExo  = insertedExercises.getOrNull(0)
        val secondExo = insertedExercises.getOrNull(1)
        val thirdExo  = insertedExercises.getOrNull(2)
        val fourthExo = insertedExercises.getOrNull(3)

        val now = System.currentTimeMillis()
        val sessionSeeds = listOf(
            SessionSeedData(
                session = Session(
                    name = "Push",
                    category = "Pectoraux / Triceps",
                    date = now - 2 * 86400000,
                    difficulty = Difficulty.EASY,
                    duration = 45L
                ),
                exerciseIds = listOfNotNull(firstExo?.id, secondExo?.id)
            ),
            SessionSeedData(
                session = Session(
                    name = "Pull",
                    category = "Dos / Biceps",
                    date = now - 86400000,
                    difficulty = Difficulty.MEDIUM,
                    duration = 30L
                ),
                exerciseIds = listOfNotNull(thirdExo?.id, fourthExo?.id)
            )
        )

        val sessionIds = sessionSeeds.map { seed -> sessionDao.insertSession(seed.session) }
        val sessionExercises = sessionSeeds.flatMapIndexed { index, seed ->
            val sessionId = sessionIds[index].toInt()
            seed.exerciseIds.map { exerciseId ->
                SessionExercise(sessionId = sessionId, exerciseId = exerciseId, isDone = true)
            }
        }
        sessionDao.insertSessionExercises(sessionExercises)
    }
}