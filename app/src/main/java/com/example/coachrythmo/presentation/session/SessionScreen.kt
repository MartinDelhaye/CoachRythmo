package com.example.coachrythmo.presentation.session

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.DifficultyBadge
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@Composable
fun SessionScreen(viewModel: SessionViewModel, navController: NavController) {

    val routine      by viewModel.routine
    val exercises    by viewModel.exercises
    val doneIds      by viewModel.doneExerciseIds
    val elapsed      by viewModel.elapsedSeconds
    val sessionSaved by viewModel.sessionSaved
    LaunchedEffect(sessionSaved) {
        if (sessionSaved) navController.popBackStack()
    }

    if (routine == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = CRPrimaryRed)
        }
        return
    }

    Scaffold(
        bottomBar = {
            Button(
                onClick = { viewModel.finishSession() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CRPrimaryRed)
            ) {
                Text("Terminer la séance", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    ) { innerPadding ->
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Annuler"
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = routine!!.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(text = routine!!.category, color = Color.Gray, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                DifficultyBadge(routine!!.difficulty)
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF0F0))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = viewModel.formatTime(elapsed),
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Bold,
                            color = CRPrimaryRed
                        )
                        Text(
                            text = "en cours",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Exercices",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (exercises.isEmpty()) {
                item {
                    Text(
                        text = "Aucun exercice lié à cette routine.",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            items(exercises) { exercise ->
                val isDone = exercise.id in doneIds

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isDone) Color(0xFFE8F5E9) else Color(0xFFFAFAFA))
                        .border(
                            width = 1.dp,
                            color = if (isDone) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { viewModel.toggleExercise(exercise.id!!) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = exercise.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = if (isDone) Color(0xFF2E7D32) else Color.Black
                        )
                        Text(
                            text = exercise.category,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = if (isDone) Color(0xFF4CAF50) else Color.Transparent,
                                shape = CircleShape
                            )
                            .border(
                                width = 2.dp,
                                color = if (isDone) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isDone) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Fait",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}