package com.example.coachrythmo.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.TodaySession
import com.example.coachrythmo.presentation.components.UpcomingSessions
import com.example.coachrythmo.presentation.components.WeekCalendar
import com.example.coachrythmo.presentation.getRoutines

@Composable
fun HomeScreen(navController: NavController) {

    val routines = getRoutines()
    val todayRoutine = routines.first()

    AppScreen(
        navController = navController,
        title = "Accueil"
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            WeekCalendar()
            TodaySession(todayRoutine)
            UpcomingSessions(routines.drop(1))
        }
    }
}