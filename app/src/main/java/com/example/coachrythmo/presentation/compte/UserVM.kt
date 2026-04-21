package com.example.coachrythmo.presentation.compte

import com.example.coachrythmo.domain.model.Difficulty

data class UserVM(
    val id: Int? = null,
    val name: String,
    val mail: String,
    val goal : String
)