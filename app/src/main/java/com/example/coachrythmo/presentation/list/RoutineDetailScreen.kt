package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.domain.model.Exercise
import com.example.coachrythmo.presentation.components.DifficultyBadge
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import com.example.coachrythmo.ui.theme.CRWhite

@Composable
fun RoutineDetail(
    navController: NavController,
    viewModel: ListRoutinesViewsModel,
    routineId: Int
) {
    val routine = viewModel.routines.value.firstOrNull { it.id == routineId } ?: return
    val routineExercises = viewModel.routineExercises.value
    val allExercises = viewModel.allExercises.value
    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(routineId) {
        viewModel.loadExercisesForRoutine(routineId)
    }

    if (showAddDialog) {
        val available = allExercises
            .filter { ex -> routineExercises.none { it.id == ex.id } }
            .filter { it.name.contains(searchQuery, ignoreCase = true) }

        AlertDialog(
            onDismissRequest = { showAddDialog = false; searchQuery = "" },
            title = { Text("Ajouter un exercice") },
            text = {
                Column {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Rechercher...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                        items(available) { exercise ->
                            TextButton(
                                onClick = {
                                    viewModel.addExerciseToRoutine(routineId, exercise.id)
                                    showAddDialog = false
                                    searchQuery = ""
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(exercise.name, fontWeight = FontWeight.Medium, color = Color.Black)
                                    Text(exercise.category, fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAddDialog = false; searchQuery = "" }) {
                    Text("Annuler")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CRWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CRPrimaryRed)
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = Color.White)
            }
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(routine.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                DifficultyBadge(routine.difficulty)
            }
        }

        LazyColumn(modifier = Modifier.padding(20.dp)) {
            item {
                Text("Informations", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(label = "Catégorie", value = routine.category)
                InfoRow(label = "Jour", value = routine.day)
                InfoRow(label = "Heure", value = routine.startTime)
                routine.durationMinutes?.let { InfoRow(label = "Durée", value = "$it min") }
                if (routine.description.isNotBlank()) InfoRow(label = "Description", value = routine.description)
                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Exercices", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Ajouter", tint = CRPrimaryRed)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (routineExercises.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF0F0), RoundedCornerShape(12.dp))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Aucun exercice — appuie sur + pour en ajouter", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            } else {
                items(routineExercises) { exercise ->
                    ExerciseRow(
                        exercise = exercise,
                        onDelete = { viewModel.removeExerciseFromRoutine(routineId, exercise.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseRow(exercise: Exercise, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(exercise.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(exercise.category, fontSize = 12.sp, color = Color.Gray)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
        }
    }
    HorizontalDivider(color = Color(0xFFEEEEEE))
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.Black)
    }
    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
}