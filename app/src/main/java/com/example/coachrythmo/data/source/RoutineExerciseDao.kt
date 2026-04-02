package com.example.coachrythmo.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef

@Dao
interface RoutineExerciseDao {

    @Insert
    suspend fun insertAll(crossRefs: List<RoutineExerciseCrossRef>)

    @Query("""
        SELECT exercises.* FROM exercises
        INNER JOIN routine_exercise
        ON exercises.id = routine_exercise.exerciseId
        WHERE routine_exercise.routineId = :routineId
    """)
    suspend fun getExercisesForRoutine(routineId: Int): List<Exercise>
}
