package com.example.coachrythmo.presentation.suivi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.SessionCard
import com.example.coachrythmo.domain.model.Session
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        sessions.forEach { item ->

            val formattedDate = formatter.format(Date(item.session.date))
                .replaceFirstChar { it.uppercase() }

            SessionCard(
                session = item.session,
                dateText = formattedDate,
                done = item.doneExercises,
                total = item.totalExercises
            )
        }
    }
}

