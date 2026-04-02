package com.example.coachrythmo.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise

@Database(
    entities = [
        Routine::class,
        Exercise::class,
        RoutineExerciseCrossRef::class,
        Session::class,
        SessionExercise::class
    ],
    version = 4
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineExerciseDao(): RoutineExerciseDao
    abstract fun sessionDao(): SessionDao

    companion object {
        const val DATABASE_NAME = "coachrythmo.db"
    }
}