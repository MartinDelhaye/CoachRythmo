package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(navController: NavController, viewModel: ListRoutinesViewsModel) {

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedDay by remember { mutableStateOf("Lundi") }
    var startTime by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.EASY) }

    val days = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    var dayExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nouvelle Routine", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom de la séance *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optionnel)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Catégorie *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenuBox(
                expanded = dayExpanded,
                onExpandedChange = { dayExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedDay,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Jour *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dayExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = dayExpanded,
                    onDismissRequest = { dayExpanded = false }
                ) {
                    days.forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day) },
                            onClick = { selectedDay = day; dayExpanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Heure de début *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it.filter { c -> c.isDigit() } },
                label = { Text("Durée (minutes)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Difficulté *", fontSize = 16.sp, fontWeight = FontWeight.Medium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Difficulty.entries.forEach { diff ->
                    val isSelected = selectedDifficulty == diff
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedDifficulty = diff },
                        label = { Text(diff.label) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = diff.color,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && category.isNotBlank() && startTime.isNotBlank()) {
                        viewModel.saveRoutine(
                            RoutineVM(
                                name = name,
                                description = description,
                                category = category,
                                day = selectedDay,
                                startTime = startTime,
                                difficulty = selectedDifficulty,
                                durationMinutes = duration.toIntOrNull()
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CRPrimaryRed)
            ) {
                Text("Enregistrer", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}