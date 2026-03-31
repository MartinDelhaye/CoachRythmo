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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@Composable
fun TodaySession(routine: RoutineVM) {
    Column{
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

        // fake data pour l’instant
        Text("Glutes - 4 x 15")
        Text("Squads - 3 x 12")
        Text("Glutes - 4 x 15")
        Text("Squads - 3 x 12")

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = { },
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