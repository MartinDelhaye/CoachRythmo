package com.example.coachrythmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.list.AddRoutineScreen
import com.example.coachrythmo.presentation.list.ListRoutinesScreen
import com.example.coachrythmo.presentation.list.ListRoutinesViewsModel
import com.example.coachrythmo.ui.theme.CoachRythmoTheme
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoachRythmoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val sharedViewModel = viewModel<ListRoutinesViewsModel>()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RoutinesListScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.RoutinesListScreen.route) {
                            ListRoutinesScreen(navController, sharedViewModel)
                        }
                        composable(route = Screen.AddRoutineScreen.route) {
                            AddRoutineScreen(navController, sharedViewModel)
                        }
                    }
                }
            }
        }
    }
}