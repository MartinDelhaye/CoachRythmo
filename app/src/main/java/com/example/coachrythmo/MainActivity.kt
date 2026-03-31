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
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.components.CustomMenu
import com.example.coachrythmo.presentation.home.HomeScreen
import com.example.coachrythmo.presentation.list.AddRoutineScreen
import com.example.coachrythmo.presentation.list.ListRoutinesScreen
import com.example.coachrythmo.presentation.list.ListRoutinesViewsModel
import com.example.coachrythmo.ui.theme.CoachRythmoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoachRythmoTheme {
                val navController = rememberNavController()
                val sharedViewModel = viewModel<ListRoutinesViewsModel>()
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
                            HomeScreen(navController)
                        }

                        composable(Screen.RoutinesListScreen.route) {
                            ListRoutinesScreen(navController, sharedViewModel)
                        }

                        // Ta route ajoutée
                        composable(Screen.AddRoutineScreen.route) {
                            AddRoutineScreen(navController, sharedViewModel)
                        }

                        composable(Screen.SuiviScreen.route) {
                            Text("Page Suivi", style = MaterialTheme.typography.titleLarge)
                        }

                        composable(Screen.CompteScreen.route) {
                            Text("Page Compte", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}