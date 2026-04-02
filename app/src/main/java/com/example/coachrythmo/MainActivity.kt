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
import androidx.room.Room
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
import com.example.coachrythmo.auth.AuthManager
import com.example.coachrythmo.presentation.auth.LoginScreen
import com.example.coachrythmo.presentation.auth.VerifyCodeScreen

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val authManager = AuthManager(applicationContext)

            val startDestination = if (authManager.isLoggedIn()) {
                Screen.HomeScreen.route
            } else {
                Screen.Login.route
            }
            LaunchedEffect(Unit) {
                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {

                    val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                    val isFirstLaunch = prefs.getBoolean("is_first_launch", true)

                    if (isFirstLaunch) {
                        db.clearAllTables()
                        db.routineDao().insertAll(SeedData.getRoutines())
                        prefs.edit().putBoolean("is_first_launch", false).apply()
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
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Login.route) {
                            LoginScreen(navController, authManager)
                        }

                        composable(Screen.Verify.route) {
                            VerifyCodeScreen(navController, authManager)
                        }

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