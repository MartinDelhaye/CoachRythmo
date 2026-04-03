package com.example.coachrythmo.presentation.suivi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.RoutineCardWithDate
import com.example.coachrythmo.presentation.RoutineVM
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SuiviScreen(
    navController: NavController,
    viewModel: SuiviViewModel
) {

    val routines by viewModel.routines

    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH)

    AppScreen(
        navController = navController,
        title = "Suivi"
    ) {
        routines.forEach { routine ->

            val date = getNextDateFromDay(routine.day)

            val formattedDate = date.format(formatter)
                .replaceFirstChar { it.uppercase() }

            RoutineCardWithDate(
                routine = routine,
                dateText = formattedDate
            )
        }
    }
}
fun getNextDateFromDay(day: String): LocalDate {

    val today = LocalDate.now()

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

    var date = today.with(targetDay)

    // Si le jour est déjà passé → on prend la semaine suivante
    if (date.isBefore(today)) {
        date = date.plusWeeks(1)
    }

    return date
}
