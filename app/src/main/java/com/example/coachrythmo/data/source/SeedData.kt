package com.example.coachrythmo.data.source

import com.example.coachrythmo.domain.model.*

object SeedData {

    suspend fun seedDatabase(
        routineDao: RoutineDao,
        exerciseDao: ExerciseDao,
        crossRefDao: RoutineExerciseDao
    ) {
        routineDao.clear()
        exerciseDao.clear()

        val exercises = listOf(
            Exercise(name = "Développé couché", category = "Pectoraux"),
            Exercise(name = "Pompes",           category = "Pectoraux"),
            Exercise(name = "Tractions",        category = "Dos"),
            Exercise(name = "Curl biceps",      category = "Dos"),
            Exercise(name = "Squats",           category = "Jambes"),
            Exercise(name = "Fentes",           category = "Jambes"),
            Exercise(name = "Course à pied",    category = "Cardio")
        )
        exerciseDao.insertAll(exercises)
        val insertedExercises = exerciseDao.getAllNow()

        val routines = listOf(
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
        routineDao.insertAll(routines)
        val insertedRoutines = routineDao.getAllNow()

        val push = insertedRoutines.first { it.name == "Push" }
        val pull = insertedRoutines.first { it.name == "Pull" }
        val legs = insertedRoutines.first { it.name == "Leg Day" }

        val crossRefs = listOf(
            RoutineExerciseCrossRef(push.id!!, insertedExercises.first { it.name == "Développé couché" }.id!!),
            RoutineExerciseCrossRef(push.id,   insertedExercises.first { it.name == "Pompes" }.id!!),
            RoutineExerciseCrossRef(pull.id!!, insertedExercises.first { it.name == "Tractions" }.id!!),
            RoutineExerciseCrossRef(pull.id,   insertedExercises.first { it.name == "Curl biceps" }.id!!),
            RoutineExerciseCrossRef(legs.id!!, insertedExercises.first { it.name == "Squats" }.id!!),
            RoutineExerciseCrossRef(legs.id,   insertedExercises.first { it.name == "Fentes" }.id!!)
        )
        crossRefDao.insertAll(crossRefs)
    }
}