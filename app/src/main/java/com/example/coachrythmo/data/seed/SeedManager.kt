package com.example.coachrythmo.data.seed

import com.example.coachrythmo.data.source.ExerciseDao
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.data.source.RoutineExerciseDao
import com.example.coachrythmo.data.source.SessionDao
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef

object SeedManager {

    suspend fun seedDatabase(
        routineDao: RoutineDao,
        exerciseDao: ExerciseDao,
        crossRefDao: RoutineExerciseDao,
        sessionDao: SessionDao
    ) {
        sessionDao.clearSessionExercises()
        sessionDao.clearSessions()
        crossRefDao.clear()
        routineDao.clear()
        exerciseDao.clear()

        val exercises = ExerciseSeed.getExercises()
        exerciseDao.insertAll(exercises)
        val insertedExercises = exerciseDao.getAllNow()

        val routines = RoutineSeed.getRoutines()
        routineDao.insertAll(routines)
        val insertedRoutines = routineDao.getAllNow()

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
    }
}