package com.example.coachrythmo.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.RoutineVM
import com.example.coachrythmo.ui.theme.CRPrimaryRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineScreen(navController: NavController, viewModel: ListRoutinesViewsModel) {

    val days = listOf("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche")
    var dayExpanded by remember { mutableStateOf(false) }
    var showExerciseDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val allExercises = viewModel.allExercises.value

    // Récupération des coordonnées retournées par MapPickerScreen
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val returnedLat = savedStateHandle?.getStateFlow<Double?>("latitude", null)?.collectAsState()
    val returnedLng = savedStateHandle?.getStateFlow<Double?>("longitude", null)?.collectAsState()

    // On écrit directement dans le ViewModel — pas de copie locale
    LaunchedEffect(returnedLat?.value, returnedLng?.value) {
        returnedLat?.value?.let { viewModel.formLatitude = it }
        returnedLng?.value?.let { viewModel.formLongitude = it }
    }

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
                value = viewModel.formName,
                onValueChange = { viewModel.formName = it },
                label = { Text("Nom de la séance *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.formDescription,
                onValueChange = { viewModel.formDescription = it },
                label = { Text("Description (optionnel)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3
            )

            OutlinedTextField(
                value = viewModel.formCategory,
                onValueChange = { viewModel.formCategory = it },
                label = { Text("Catégorie *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            ExposedDropdownMenuBox(
                expanded = dayExpanded,
                onExpandedChange = { dayExpanded = it }
            ) {
                OutlinedTextField(
                    value = viewModel.formDay,
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
                            onClick = { viewModel.formDay = day; dayExpanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = viewModel.formStartTime,
                onValueChange = { viewModel.formStartTime = it },
                label = { Text("Heure de début *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.formDuration,
                onValueChange = { viewModel.formDuration = it.filter { c -> c.isDigit() } },
                label = { Text("Durée (minutes)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedButton(
                onClick = { navController.navigate(Screen.MapPickerScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (viewModel.formLatitude != null)
                        "📍 Lieu défini (${String.format("%.4f", viewModel.formLatitude)}, ${String.format("%.4f", viewModel.formLongitude)})"
                    else
                        "📍 Choisir un lieu (optionnel)"
                )
            }

            Text("Difficulté *", fontSize = 16.sp, fontWeight = FontWeight.Medium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Difficulty.entries.forEach { diff ->
                    val isSelected = viewModel.formDifficulty == diff
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.formDifficulty = diff },
                        label = { Text(diff.label) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = diff.color,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            OutlinedButton(
                onClick = { showExerciseDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (viewModel.formExerciseIds.isEmpty()) "Ajouter des exercices (optionnel)"
                    else "${viewModel.formExerciseIds.size} exercice(s) sélectionné(s)"
                )
            }

            if (showExerciseDialog) {
                val available = allExercises.filter { it.name.contains(searchQuery, ignoreCase = true) }
                AlertDialog(
                    onDismissRequest = { showExerciseDialog = false; searchQuery = "" },
                    title = { Text("Choisir des exercices") },
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
                                    val isSelected = viewModel.formExerciseIds.contains(exercise.id)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.formExerciseIds =
                                                    if (isSelected) viewModel.formExerciseIds - exercise.id
                                                    else viewModel.formExerciseIds + exercise.id
                                            }
                                            .padding(vertical = 8.dp, horizontal = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(checked = isSelected, onCheckedChange = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(exercise.name, fontWeight = FontWeight.Medium)
                                            Text(exercise.category, fontSize = 12.sp, color = Color.Gray)
                                        }
                                    }
                                    HorizontalDivider()
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showExerciseDialog = false; searchQuery = "" }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExerciseDialog = false; searchQuery = "" }) {
                            Text("Annuler")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    android.util.Log.d(
                        "ADD_ROUTINE",
                        "name='${viewModel.formName}' category='${viewModel.formCategory}' startTime='${viewModel.formStartTime}'"
                    )
                    if (viewModel.formName.isNotBlank() &&
                        viewModel.formCategory.isNotBlank() &&
                        viewModel.formStartTime.isNotBlank()
                    ) {
                        viewModel.saveRoutineWithExercises(
                            RoutineVM(
                                name = viewModel.formName,
                                description = viewModel.formDescription,
                                category = viewModel.formCategory,
                                day = viewModel.formDay,
                                startTime = viewModel.formStartTime,
                                difficulty = viewModel.formDifficulty,
                                durationMinutes = viewModel.formDuration.toIntOrNull(),
                                latitude = viewModel.formLatitude,
                                longitude = viewModel.formLongitude
                            ),
                            viewModel.formExerciseIds
                        )
                        viewModel.resetForm()   // ← nettoyage après sauvegarde
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CRPrimaryRed)
            ) {
                Text("Enregistrer", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}