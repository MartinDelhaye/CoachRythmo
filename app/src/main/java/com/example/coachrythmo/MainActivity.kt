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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coachrythmo.data.source.AppDatabase
import com.example.coachrythmo.data.source.SeedData
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.components.CustomMenu
import com.example.coachrythmo.presentation.home.HomeScreen
import com.example.coachrythmo.presentation.home.HomeViewModel
import com.example.coachrythmo.presentation.list.AddRoutineScreen
import com.example.coachrythmo.presentation.list.ListRoutinesScreen
import com.example.coachrythmo.presentation.list.ListRoutinesViewsModel
import com.example.coachrythmo.ui.theme.CoachRythmoTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    if (db.routineDao().count() == 0) {
                        db.routineDao().insertAll(SeedData.getRoutines())
                    }
                }
            }
            CoachRythmoTheme {
                val navController = rememberNavController()

                // ViewModel partagé pour la liste des routines
                val listViewModel = viewModel<ListRoutinesViewsModel> {
                    ListRoutinesViewsModel(db.routineDao())
                }

                // ViewModel dédié au HomeScreen
                val homeViewModel = viewModel<HomeViewModel> {
                    HomeViewModel(db.routineDao())
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { CustomMenu(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(navController, homeViewModel)
                        }

                        composable(Screen.RoutinesListScreen.route) {
                            ListRoutinesScreen(navController, listViewModel)
                        }

                        composable(Screen.AddRoutineScreen.route) {
                            AddRoutineScreen(navController, listViewModel)
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