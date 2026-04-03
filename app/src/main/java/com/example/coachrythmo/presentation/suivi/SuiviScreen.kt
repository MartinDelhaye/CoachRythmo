package com.example.coachrythmo.presentation.suivi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.SessionCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SuiviScreen(
    navController: NavController,
    viewModel: SuiviViewModel
) {

    val sessions by viewModel.sessions.collectAsState()

    val formatter = SimpleDateFormat("EEEE dd MMMM", Locale.FRENCH)

    AppScreen(
        navController = navController,
        title = "Suivi"
    ) {
        sessions.forEach { session ->

            val formattedDate = formatter.format(Date(session.date))
                .replaceFirstChar { it.uppercase() }

            SessionCard(
                name = session.name,
                category = session.category,
                difficulty = session.difficulty,
                dateText = formattedDate,
                done = session.doneExercises,
                total = session.totalExercises
            )
        }
    }
}