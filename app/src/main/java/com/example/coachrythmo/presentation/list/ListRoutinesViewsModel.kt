package com.example.coachrythmo.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.ExerciseDao
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.data.source.RoutineExerciseDao
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.domain.model.RoutineExerciseCrossRef
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.presentation.toEntity
import com.example.coachrythmo.presentation.toVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class ListRoutinesViewsModel(
    private val dao: RoutineDao,
    private val exerciseDao: ExerciseDao,
    private val routineExerciseDao: RoutineExerciseDao
) : ViewModel() {

    private val _routines = mutableStateOf<List<RoutineVM>>(emptyList())
    val routines: State<List<RoutineVM>> = _routines

    private val _routineExercises = mutableStateOf<List<Exercise>>(emptyList())
    val routineExercises: State<List<Exercise>> = _routineExercises

    private val _allExercises = mutableStateOf<List<Exercise>>(emptyList())
    val allExercises: State<List<Exercise>> = _allExercises

    private var job: Job? = null

    // State du formulaire qui survit à la navigation
    var formName by mutableStateOf("")
    var formDescription by mutableStateOf("")
    var formCategory by mutableStateOf("")
    var formDay by mutableStateOf("Lundi")
    var formStartTime by mutableStateOf("")
    var formDuration by mutableStateOf("")
    var formDifficulty by mutableStateOf(Difficulty.EASY)
    var formLatitude by mutableStateOf<Double?>(null)
    var formLongitude by mutableStateOf<Double?>(null)
    var formExerciseIds by mutableStateOf<List<Int>>(emptyList())

    fun resetForm() {
        formName = ""
        formDescription = ""
        formCategory = ""
        formDay = "Lundi"
        formStartTime = ""
        formDuration = ""
        formDifficulty = Difficulty.EASY
        formLatitude = null
        formLongitude = null
        formExerciseIds = emptyList()
    }

    init {
        loadRoutines()
        loadAllExercises()
    }

    private fun loadRoutines() {
        job?.cancel()
        job = dao.getRoutines()
            .onEach { list -> _routines.value = list.map { it.toVM() } }
            .launchIn(viewModelScope)
    }

    private fun loadAllExercises() {
        viewModelScope.launch {
            _allExercises.value = exerciseDao.getAllNow()
        }
    }

    fun loadExercisesForRoutine(routineId: Int) {
        viewModelScope.launch {
            _routineExercises.value = routineExerciseDao.getExercisesForRoutine(routineId)
        }
    }

    fun addExerciseToRoutine(routineId: Int, exerciseId: Int) {
        viewModelScope.launch {
            routineExerciseDao.insert(RoutineExerciseCrossRef(routineId, exerciseId))
            loadExercisesForRoutine(routineId)
        }
    }

    fun removeExerciseFromRoutine(routineId: Int, exerciseId: Int) {
        viewModelScope.launch {
            routineExerciseDao.delete(routineId, exerciseId)
            loadExercisesForRoutine(routineId)
        }
    }

    fun saveRoutineWithExercises(routineVM: RoutineVM, exerciseIds: List<Int>) {
        viewModelScope.launch {
            android.util.Log.d("SAVE_ROUTINE", "Sauvegarde: ${routineVM.name}, exercices: $exerciseIds")
            dao.insert(routineVM.toEntity())
            if (exerciseIds.isNotEmpty()) {
                val lastRoutine = dao.getAllNow().lastOrNull()
                android.util.Log.d("SAVE_ROUTINE", "Dernière routine: ${lastRoutine?.name}, id: ${lastRoutine?.id}")
                lastRoutine ?: return@launch
                exerciseIds.forEach { exerciseId ->
                    routineExerciseDao.insert(RoutineExerciseCrossRef(lastRoutine.id!!, exerciseId))
                }
            }
        }
    }
}