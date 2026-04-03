package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.domain.model.Routine
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@Composable
fun TodaySession(
    navController: NavController,
    routine: RoutineVM,
    viewModel: TodaySessionViewModel
) {
    val exercises by viewModel.exercises

    LaunchedEffect(routine.id) {
        routine.id?.let { viewModel.loadExercises(it) }
    }

    Column {

        Text(
            text = "Séance du jour",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        RoutineCard(routine = routine)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Détail de la séance"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ VRAIS EXOS
        exercises.forEach { exercise ->
            ExerciseItem(exercise)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    navController.navigate(
                        Screen.SessionScreen.createRoute(routine.id!!)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CRPrimaryRed
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text("Commencer")
            }
        }
    }
}