package com.example.coachrythmo.presentation.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.RoutineExerciseDao
import com.example.coachrythmo.domain.model.Exercise
import kotlinx.coroutines.launch

class TodaySessionViewModel(
    private val routineExerciseDao: RoutineExerciseDao
) : ViewModel() {

    private val _exercises = mutableStateOf<List<Exercise>>(emptyList())
    val exercises: State<List<Exercise>> = _exercises

    fun loadExercises(routineId: Int) {
        viewModelScope.launch {
            _exercises.value =
                routineExerciseDao.getExercisesForRoutine(routineId)
        }
    }
}