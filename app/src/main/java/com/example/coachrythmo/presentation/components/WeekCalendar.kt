package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.domain.model.CalendarItem
import com.example.coachrythmo.ui.theme.CRDark
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import com.example.coachrythmo.ui.theme.CRWhite
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WeekCalendar()
{
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.MONDAY)

    val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    val calendarItems = weekDates.map { date ->
        CalendarItem(
            dayNumber = date.dayOfMonth,
            isToday = date == today
        )
    }
    val daysLetters = listOf("L", "M", "M", "J", "V", "S", "D")

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            daysLetters.forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            calendarItems.forEach { item ->

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (item.isToday) CRPrimaryRed else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.dayNumber.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (item.isToday) CRWhite else CRDark
                    )
                }
            }
        }
    }
}