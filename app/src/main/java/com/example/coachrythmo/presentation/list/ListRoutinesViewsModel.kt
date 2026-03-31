package com.example.coachrythmo.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.presentation.addRoutine
import com.example.coachrythmo.presentation.getRoutines

class ListRoutinesViewsModel : ViewModel() {
    private val _routines: MutableState<List<RoutineVM>> = mutableStateOf(emptyList())
    var routines: State<List<RoutineVM>> = _routines

    init {
        _routines.value = getRoutines()
    }

    fun saveRoutine(routine: RoutineVM) {
        addRoutine(routine)
        _routines.value = getRoutines()
    }
}