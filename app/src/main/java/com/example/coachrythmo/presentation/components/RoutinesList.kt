package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.domain.model.Routine

@Composable
fun RoutinesList(routines: List<Routine>) {

    Column {

        Text(text = "Toutes les routines")

        Spacer(modifier = Modifier.height(8.dp))

        if (routines.isEmpty()) {
            Text(text = "Aucune routine")
        } else {
            routines.forEach { routine ->

                Text(text = routine.toString()) // debug

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
