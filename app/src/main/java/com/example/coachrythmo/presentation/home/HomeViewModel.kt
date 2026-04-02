package com.example.coachrythmo.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.data.source.SessionDao
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.presentation.toVM
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar

data class UpcomingSession(
    val routine: RoutineVM,
    val date: LocalDate
)

class HomeViewModel(
    private val dao: RoutineDao,
    private val sessionDao: SessionDao
) : ViewModel() {

    private val _todayRoutine = mutableStateOf<RoutineVM?>(null)
    val todayRoutine: State<RoutineVM?> = _todayRoutine

    private val _upcomingSessions = mutableStateOf<List<UpcomingSession>>(emptyList())
    val upcomingSessions: State<List<UpcomingSession>> = _upcomingSessions

    private val _sessionDoneToday = mutableStateOf(false)
    val sessionDoneToday: State<Boolean> = _sessionDoneToday

    private var job: Job? = null

    init {
        loadRoutines()
    }

    private fun loadRoutines() {
        job?.cancel()
        job = dao.getRoutines()
            .onEach { list ->
                val routines = list.map { it.toVM() }
                val today = LocalDate.now()
                val todayIndex = today.dayOfWeek.value // 1=Lundi ... 7=Dimanche

                // Séance du jour
                _todayRoutine.value = routines.firstOrNull { dayToIndex(it.day) == todayIndex }

                // 3 prochaines séances — on cherche semaine par semaine si besoin
                if (routines.isEmpty()) {
                    _upcomingSessions.value = emptyList()
                    return@onEach
                }

                val upcoming = mutableListOf<UpcomingSession>()
                var weekOffset = 0L

                while (upcoming.size < 3 && weekOffset < 52) {
                    val startOfWeek = today.with(DayOfWeek.MONDAY).plusWeeks(weekOffset)

                    val candidatesThisWeek = routines
                        .filter { routine ->
                            val dayIdx = dayToIndex(routine.day)
                            if (weekOffset == 0L) dayIdx > todayIndex else true
                        }
                        .map { routine ->
                            val date = startOfWeek.plusDays((dayToIndex(routine.day) - 1).toLong())
                            UpcomingSession(routine = routine, date = date)
                        }
                        .sortedBy { it.date }

                    upcoming.addAll(candidatesThisWeek)
                    weekOffset++
                }

                _upcomingSessions.value = upcoming.take(3)
            }
            .launchIn(viewModelScope)
    }

    private fun dayToIndex(day: String): Int {
        return when (day.lowercase()) {
            "lundi"    -> 1
            "mardi"    -> 2
            "mercredi" -> 3
            "jeudi"    -> 4
            "vendredi" -> 5
            "samedi"   -> 6
            "dimanche" -> 7
            else       -> 0
        }
    }

    fun checkIfSessionDoneToday() {
        viewModelScope.launch {
            val startOfDay = getStartOfDay()
            val endOfDay = getEndOfDay()

            val count = sessionDao.countSessionsBetween(startOfDay, endOfDay)
            _sessionDoneToday.value = count > 0
        }
    }

    fun getStartOfDay(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun getEndOfDay(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        return cal.timeInMillis
    }
}