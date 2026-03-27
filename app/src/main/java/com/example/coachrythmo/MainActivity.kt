package com.example.coachrythmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coachrythmo.presentation.list.ListRoutinesViewsModel
import com.example.coachrythmo.ui.theme.CoachRythmoTheme
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.home.HomeScreen
import com.example.coachrythmo.presentation.components.CustomMenu
import com.example.coachrythmo.presentation.list.ListRoutinesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoachRythmoTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        CustomMenu(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen()
                        }

                        composable(Screen.RoutinesListScreen.route) {
                            val routines = viewModel<ListRoutinesViewsModel>()
                            ListRoutinesScreen(navController, routines)
                        }

                        composable(Screen.SuiviScreen.route) {
                            // Add le Screen de la page Suivi ici
                            Text("Page Suivi", style = MaterialTheme.typography.titleLarge)
                        }

                        composable(Screen.CompteScreen.route) {
                            // Add le Screen de la page Suivi ici
                            Text("Page Compte", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}