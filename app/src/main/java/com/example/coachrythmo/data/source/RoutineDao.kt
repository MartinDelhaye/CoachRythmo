package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Query("SELECT * FROM routines")
    fun getRoutines(): Flow<List<Routine>>

    @Query("SELECT * FROM routines WHERE id = :id")
    suspend fun getRoutineById(id: Int): Routine?

    @Query("SELECT COUNT(*) FROM routines")
    suspend fun count(): Int

    @Insert
    suspend fun insertAll(routines: List<Routine>)
    @Insert
    suspend fun insert(routine: Routine)
}