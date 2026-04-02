package com.example.coachrythmo.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.TodaySession
import com.example.coachrythmo.presentation.components.UpcomingSessions
import com.example.coachrythmo.presentation.components.WeekCalendar
import androidx.compose.material3.Button
import com.example.coachrythmo.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    authManager: com.example.coachrythmo.auth.AuthManager
)
 {

    val todayRoutine by viewModel.todayRoutine
    val upcomingSessions by viewModel.upcomingSessions

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

            if (todayRoutine != null) {
                TodaySession(todayRoutine!!)
            } else {
                Text(
                    text = "Aucune séance prévue aujourd'hui 🎉",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            UpcomingSessions(upcomingSessions)
            Button(
                onClick = {
                    authManager.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Se déconnecter")
            }

        }
    }
}