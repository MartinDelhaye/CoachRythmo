package com.example.coachrythmo.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.auth.AuthManager
import com.example.coachrythmo.navigation.Screen

@Composable
fun VerifyCodeScreen(navController: NavController, authManager: AuthManager) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Code reçu", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Code SMS") }
        )

        Button(onClick = {
            if (authManager.verifyCode(code)) {
                authManager.login()
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            } else {
                error = true
            }
        }) {
            Text("Valider")
        }

        if (error) {
            Text("Code incorrect", color = Color.Red)
        }
    }
}
