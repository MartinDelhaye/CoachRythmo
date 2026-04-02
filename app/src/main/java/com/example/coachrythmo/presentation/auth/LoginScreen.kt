package com.example.coachrythmo.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.auth.AuthManager
import com.example.coachrythmo.navigation.Screen
import androidx.compose.runtime.Composable



@Composable
fun LoginScreen(navController: NavController, authManager: AuthManager) {
    var phone by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Connexion", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Numéro de téléphone") }
        )

        Button(onClick = {
            if (authManager.sendCode(phone)) {
                navController.navigate(Screen.Verify.route)
            }
        }) {
            Text("Envoyer le code")
        }
    }
}
