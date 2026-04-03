package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {


    @Insert
    suspend fun insertSessionExercise(sessionExercise: SessionExercise)

    @Insert
    suspend fun insertSession(session: Session): Long

    @Insert
    suspend fun insertSessionExercises(list: List<SessionExercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<Session>): List<Long>

    @Query("SELECT COUNT(*) FROM sessions")
    suspend fun count(): Int

    @Query("""
        SELECT COUNT(*) 
        FROM sessions 
        WHERE date BETWEEN :start AND :end
    """)
    suspend fun countSessionsBetween(start: Long, end: Long): Int

    @Query("SELECT * FROM sessions ORDER BY date ASC")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions ORDER BY date ASC")
    suspend fun getAllSessionsOnce(): List<Session>

    @Query("DELETE FROM sessions")
    suspend fun clearSessions()

    @Query("DELETE FROM session_exercises")
    suspend fun clearSessionExercises()

    @Query("""
        SELECT COUNT(*) 
        FROM session_exercises 
        WHERE sessionId = :sessionId
    """)
    suspend fun countExercises(sessionId: Int): Int

    @Query("""
        SELECT COUNT(*) 
        FROM session_exercises 
        WHERE sessionId = :sessionId AND isDone = 1
    """)
    suspend fun countDoneExercises(sessionId: Int): Int
}
