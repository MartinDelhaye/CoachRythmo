package com.example.coachrythmo.presentation.suivi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coachrythmo.data.source.RoutineDao
import com.example.coachrythmo.presentation.RoutineVM
import kotlinx.coroutines.launch
import com.example.coachrythmo.presentation.toVM
import java.time.DayOfWeek
import java.time.LocalDate



class SuiviViewModel(
    private val dao: RoutineDao
) : ViewModel() {

    private val _routines = mutableStateOf<List<RoutineVM>>(emptyList())
    val routines: State<List<RoutineVM>> = _routines

    init {
        loadRoutines()
    }

    private fun getDateFromDay(day: String, reference: LocalDate): LocalDate {

        val targetDay = when (day.lowercase()) {
            "lundi" -> DayOfWeek.MONDAY
            "mardi" -> DayOfWeek.TUESDAY
            "mercredi" -> DayOfWeek.WEDNESDAY
            "jeudi" -> DayOfWeek.THURSDAY
            "vendredi" -> DayOfWeek.FRIDAY
            "samedi" -> DayOfWeek.SATURDAY
            "dimanche" -> DayOfWeek.SUNDAY
            else -> DayOfWeek.MONDAY
        }

        var date = reference.with(targetDay)

        // IMPORTANT : on récupère la prochaine occurrence
        if (date.isBefore(reference)) {
            date = date.plusWeeks(1)
        }

        return date
    }


    private fun loadRoutines() {
        viewModelScope.launch {

            val result = dao.getAll()

            val today = LocalDate.now()

            _routines.value = result
                .sortedBy { routine -> getDateFromDay(routine.day, today) }
                .map { it.toVM() }
        }
    }



}
