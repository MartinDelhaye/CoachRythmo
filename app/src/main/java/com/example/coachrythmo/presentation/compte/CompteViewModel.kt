package com.example.coachrythmo.presentation.compte

import androidx.lifecycle.ViewModel

class CompteViewModel(
) : ViewModel() {

    val user = UserVM(
        id = 1,
        name = " Mamadou",
        mail = "mamadou@gmail.com",
        goal="Perte de poids"
    )
}