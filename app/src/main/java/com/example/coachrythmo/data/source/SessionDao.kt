package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session): Long

    @Insert
    suspend fun insertSessionExercise(sessionExercise: SessionExercise)

    @Query(
    """
    SELECT COUNT(*) FROM sessions 
    WHERE date BETWEEN :start AND :end
    """)
    suspend fun countSessionsBetween(start: Long, end: Long): Int


    @Query("DELETE FROM sessions")
    suspend fun clearSessions()

    @Query("DELETE FROM session_exercises")
    suspend fun clearSessionExercises()
}