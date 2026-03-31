package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.presentation.RoutineVM
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UpcomingSessions(routines: List<RoutineVM>) {

    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH)
    val todayIndex = LocalDate.now().dayOfWeek.value

    val upcoming = routines
        .filter { dayToIndex(it.day) > todayIndex }
        .sortedBy { dayToIndex(it.day) }

    Column {

        Text(
            text = "Séances des prochains jours",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        upcoming.forEach { routine ->

            Column {

                val date = getNextDateForDay(routine.day)
                val formattedDate = date.format(formatter)

                Text(
                    text = formattedDate.replaceFirstChar { it.uppercase() }
                )

                Spacer(modifier = Modifier.height(4.dp))

                RoutineCard(routine = routine)

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

fun dayToIndex(day: String): Int {
    return when(day.lowercase()) {
        "lundi" -> 1
        "mardi" -> 2
        "mercredi" -> 3
        "jeudi" -> 4
        "vendredi" -> 5
        "samedi" -> 6
        "dimanche" -> 7
        else -> 0
    }
}

fun getNextDateForDay(day: String): LocalDate {

    val today = LocalDate.now()

    val targetDay = when(day.lowercase()) {
        "lundi" -> DayOfWeek.MONDAY
        "mardi" -> DayOfWeek.TUESDAY
        "mercredi" -> DayOfWeek.WEDNESDAY
        "jeudi" -> DayOfWeek.THURSDAY
        "vendredi" -> DayOfWeek.FRIDAY
        "samedi" -> DayOfWeek.SATURDAY
        "dimanche" -> DayOfWeek.SUNDAY
        else -> DayOfWeek.MONDAY
    }

    var date = today

    while (date.dayOfWeek != targetDay) {
        date = date.plusDays(1)
    }

    return date
}