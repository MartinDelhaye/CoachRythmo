package com.example.coachrythmo.presentation.session

import com.example.coachrythmo.domain.model.Difficulty
import com.example.coachrythmo.domain.model.Session

data class SessionVM(
    val id: Int? = null,
    val name: String,
    val category: String,
    val difficulty: Difficulty,
    val date: Long,
    val duration: Long
)

fun SessionVM.toEntity(): Session = Session(
    id = id,
    name = name,
    category = category,
    difficulty = difficulty,
    date = date,
    duration = duration
)

fun Session.toVM(): SessionVM = SessionVM(
    id = id,
    name = name,
    category = category,
    difficulty = difficulty,
    date = date,
    duration = duration
)