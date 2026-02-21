package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.RoutineCard

@Composable
fun ListRoutinesScreen(
    navController: NavController, //J'ai gardé navController car on aura forcément de la nav plus tard
    viewModel: ListRoutinesViewsModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Mes Routines",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.routines.value) { routine ->
                RoutineCard(routine = routine)
            }
        }
    }
}