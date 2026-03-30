package com.example.coachrythmo.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            WeekCalendar()
        }
    }
}