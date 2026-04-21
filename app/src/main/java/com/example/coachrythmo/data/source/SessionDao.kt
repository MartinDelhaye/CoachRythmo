package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise
import com.example.coachrythmo.domain.model.SessionWithStats
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


    @Query("""
        SELECT
            s.id            AS id,
            s.name          AS name,
            s.category      AS category,
            s.difficulty    AS difficulty,
            s.date          AS date,
            s.duration      AS duration,
            COUNT(se.exerciseId)                                  AS totalExercises,
            SUM(CASE WHEN se.isDone = 1 THEN 1 ELSE 0 END)       AS doneExercises
        FROM sessions s
        LEFT JOIN session_exercises se ON s.id = se.sessionId
        GROUP BY s.id
        ORDER BY s.date DESC
    """)
    suspend fun getSessionsWithStats(): List<SessionWithStats>

    @Query("DELETE FROM sessions")
    suspend fun clearSessions()

    @Query("DELETE FROM session_exercises")
    suspend fun clearSessionExercises()
}