package com.example.coachrythmo.presentation.compte

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.coachrythmo.presentation.components.AppScreen
import com.example.coachrythmo.presentation.components.OptionItem

@Composable
fun CompteScreen(
    navController: NavController,
    viewModel: CompteViewModel
) {
    AppScreen(
        navController = navController,
        title = "Compte"
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔴 Carte Profil
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color(0xFFE74C3C), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = viewModel.user.name.trim().first().toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = viewModel.user.name,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = viewModel.user.mail,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Bouton objectif
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE74C3C)
                        )
                    ) {
                        Text(text = viewModel.user.goal)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Options
            OptionItem(
                title = "Modifier profil",
                icon = Icons.Default.Person
            )

            OptionItem(
                title = "Changer de mot de passe",
                icon = Icons.Default.Lock
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔴 Bouton Déconnexion
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE6B8B7)
                )
            ) {
                Text("Se déconnecter", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🔴 Bouton Supprimer compte
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE74C3C)
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Supprimer le compte")
            }
        }
    }
}