package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.presentation.RoutineVM

@Composable
fun RoutineCardWithDate(
    routine: RoutineVM,
    dateText: String
) {
    Column {

        Text(text = dateText)

        Spacer(modifier = Modifier.height(4.dp))

        RoutineCard(routine = routine)
    }
}
