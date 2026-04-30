package com.example.coachrythmo.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineDao(): RoutineDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineExerciseDao(): RoutineExerciseDao
    abstract fun sessionDao(): SessionDao

    companion object {
        const val DATABASE_NAME = "coachrythmo.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration(true).build().also { INSTANCE = it }
            }
        }
    }
}