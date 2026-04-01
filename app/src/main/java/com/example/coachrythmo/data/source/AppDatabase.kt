package com.example.coachrythmo.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coachrythmo.domain.model.Routine

@Database(
    entities = [Routine::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun routineDao(): RoutineDao

    companion object {
        const val DATABASE_NAME = "coachrythmo.db"
    }
}