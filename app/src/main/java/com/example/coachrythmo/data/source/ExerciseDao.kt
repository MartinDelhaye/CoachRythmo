package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertAll(exercises: List<Exercise>)

    @Query("DELETE FROM exercises")
    suspend fun clear()

    @Query("SELECT * FROM exercises")
    suspend fun getAllNow(): List<Exercise>
}