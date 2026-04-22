package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.home.UpcomingSession
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UpcomingSessions(sessions: List<UpcomingSession>, navController: NavController) {
    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH)

    Column {
        Text(
            text = "Séances des prochains jours",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (sessions.isEmpty()) {
            Text(
                text = "Aucune séance prévue. Ajoute ta première routine !",
                style = MaterialTheme.typography.bodyMedium,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        } else {
            sessions.forEach { session ->
                Column {
                    val formattedDate = session.date.format(formatter)
                        .replaceFirstChar { it.uppercase() }
                    Text(text = formattedDate)
                    Spacer(modifier = Modifier.height(4.dp))
                    RoutineCard(
                        routine = session.routine,
                        onClick = {
                            session.routine.id?.let {
                                navController.navigate(Screen.RoutineDetail.createRoute(it))
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}