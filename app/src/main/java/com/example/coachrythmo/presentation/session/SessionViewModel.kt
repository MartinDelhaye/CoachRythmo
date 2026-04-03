package com.example.coachrythmo.presentation.session

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.data.source.RoutineExerciseDao
import com.example.coachrythmo.data.source.SessionDao
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.domain.model.Session
import com.example.coachrythmo.domain.model.SessionExercise
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.presentation.toVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SessionViewModel(
    private val routineDao: RoutineDao,
    private val routineExerciseDao: RoutineExerciseDao,
    private val sessionDao: SessionDao,
    private val routineId: Int?
) : ViewModel() {

    private val _routine = mutableStateOf<RoutineVM?>(null)
    val routine: State<RoutineVM?> = _routine

    private val _exercises = mutableStateOf<List<Exercise>>(emptyList())
    val exercises: State<List<Exercise>> = _exercises

    private val _doneExerciseIds = mutableStateOf<Set<Int>>(emptySet())
    val doneExerciseIds: State<Set<Int>> = _doneExerciseIds

    private val _elapsedSeconds = mutableStateOf(0L)
    val elapsedSeconds: State<Long> = _elapsedSeconds

    private val _sessionSaved = mutableStateOf(false)
    val sessionSaved: State<Boolean> = _sessionSaved

    private var timerJob: Job? = null
    private var startTimestamp: Long = 0L

    init {
        startTimestamp = System.currentTimeMillis()
        loadData()
        startTimer()
    }

    private fun loadData() {
        if (routineId == null) return

        viewModelScope.launch {
            val entity = routineDao.getRoutineById(routineId)
            _routine.value = entity?.toVM()
            _exercises.value = routineExerciseDao.getExercisesForRoutine(routineId)
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value++
            }
        }
    }

    fun toggleExercise(exerciseId: Int) {
        val current = _doneExerciseIds.value.toMutableSet()
        if (current.contains(exerciseId)) current.remove(exerciseId)
        else current.add(exerciseId)

        _doneExerciseIds.value = current
    }

    fun finishSession() {
        val routine = _routine.value ?: return
        timerJob?.cancel()

        viewModelScope.launch {
            val sessionId = sessionDao.insertSession(
                Session(
                    name = routine.name,
                    category = routine.category,
                    difficulty = routine.difficulty,
                    date = startTimestamp,
                    duration = _elapsedSeconds.value
                )
            ).toInt()

            _exercises.value.forEach { exercise ->
                sessionDao.insertSessionExercise(
                    SessionExercise(
                        sessionId = sessionId,
                        exerciseId = exercise.id!!,
                        isDone = exercise.id in _doneExerciseIds.value
                    )
                )
            }

            _sessionSaved.value = true
        }
    }

    fun formatTime(seconds: Long): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%02d:%02d".format(m, s)
    }
}
