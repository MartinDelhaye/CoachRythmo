package com.example.coachrythmo.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.coachrythmo.domain.model.Session

@Composable
fun SessionCard(
    session: Session,
    dateText: String,
    done: Int,
    total: Int
) {
    Text(session.name)
    Text(session.category)
    Text(dateText)

    Text("$done / $total exercices")
}

