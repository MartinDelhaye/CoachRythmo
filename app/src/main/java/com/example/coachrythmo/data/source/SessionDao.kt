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
}