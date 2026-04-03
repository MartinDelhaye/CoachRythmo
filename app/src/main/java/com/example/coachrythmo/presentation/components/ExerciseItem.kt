package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coachrythmo.domain.model.Exercise

@Composable
fun ExerciseItem(exercise: Exercise) {
    Text(
        text = exercise.name,
        style = MaterialTheme.typography.bodyLarge
    )
}