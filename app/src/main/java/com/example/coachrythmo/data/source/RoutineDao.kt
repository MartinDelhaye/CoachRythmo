Voici le fichier corrigé — je garde les deux méthodes `getAll` et `getAllNow` pour éviter les erreurs :

```kotlin
package com.example.coachrythmo.data.source

import androidx.room.*
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Query("SELECT * FROM routines")
    fun getRoutines(): Flow<List<Routine>>

    @Insert
    suspend fun insert(routine: Routine)

    @Query("SELECT * FROM routines WHERE id = :id")
    suspend fun getRoutineById(id: Int): Routine?

    @Query("SELECT * FROM routine_exercise WHERE routineId = :routineId")
    fun getRoutineExercises(routineId: Int): Flow<List<RoutineExerciseCrossRef>>

    @Query("SELECT COUNT(*) FROM routines")
    suspend fun count(): Int

    @Insert
    suspend fun insertAll(routines: List<Routine>)

    @Query("SELECT * FROM routines")
    suspend fun getAll(): List<Routine>

    @Query("SELECT * FROM routines")
    suspend fun getAllNow(): List<Routine>

    @Query("DELETE FROM routines")
    suspend fun clear()
}
```

D'autres conflits ? 🙂