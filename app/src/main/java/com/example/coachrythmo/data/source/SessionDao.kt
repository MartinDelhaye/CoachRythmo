package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise
import com.example.coachrythmo.domain.model.SessionWithStats
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    // 🔹 Session
    @Insert
    suspend fun insertSession(session: Session): Long

    @Query("SELECT * FROM sessions ORDER BY date DESC")
    fun getSessions(): Flow<List<Session>>

    // 🔹 SessionExercise
    @Insert
    suspend fun insertSessionExercise(sessionExercise: SessionExercise)

    @Insert
    suspend fun insertSessionExercises(list: List<SessionExercise>)

    @Query("SELECT * FROM session_exercises WHERE sessionId = :sessionId")
    fun getSessionExercises(sessionId: Int): Flow<List<SessionExercise>>

    @Update
    suspend fun updateSessionExercise(sessionExercise: SessionExercise)

    // 🔹 Stats
    @Query("""
        SELECT 
            s.id,
            s.name,
            s.category,
            s.difficulty,
            s.date,
            s.duration,
            COUNT(se.exerciseId) as totalExercises,
            COALESCE(SUM(CASE WHEN se.isDone = 1 THEN 1 ELSE 0 END), 0) as doneExercises
        FROM sessions s
        LEFT JOIN session_exercises se 
            ON s.id = se.sessionId
        GROUP BY s.id
        ORDER BY s.date DESC
    """)
    suspend fun getSessionsWithStats(): List<SessionWithStats>

    // 🔹 Utils
    @Query("""
        SELECT COUNT(*) 
        FROM sessions 
        WHERE date BETWEEN :start AND :end
    """)
    suspend fun countSessionsBetween(start: Long, end: Long): Int

    // 🔹 Seed / Debug
    @Query("DELETE FROM sessions")
    suspend fun clearSessions()

    @Query("DELETE FROM session_exercises")
    suspend fun clearSessionExercises()
}