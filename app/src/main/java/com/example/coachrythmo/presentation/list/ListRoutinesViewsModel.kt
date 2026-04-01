package com.example.coachrythmo.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.presentation.toEntity
import com.example.coachrythmo.presentation.toVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListRoutinesViewsModel(
    private val dao: RoutineDao
) : ViewModel() {

    private val _routines = mutableStateOf<List<RoutineVM>>(emptyList())
    val routines: State<List<RoutineVM>> = _routines

    private var job: Job? = null

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        job?.cancel()
        job = dao.getRoutines()
            .onEach { list ->
                _routines.value = list.map { it.toVM() }
            }
            .launchIn(viewModelScope)
    }

    fun saveRoutine(routineVM: RoutineVM) {
        viewModelScope.launch {
            dao.insert(routineVM.toEntity())
        }
    }
}