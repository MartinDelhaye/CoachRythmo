package com.example.coachrythmo.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Session

@Composable
fun SessionCard(
    name: String,
    category: String,
    difficulty: Difficulty,
    dateText: String,
    done: Int,
    total: Int
) {
    Text(name)
    Text(category)
    Text(dateText)

    Text("$done / $total exercices")
}

