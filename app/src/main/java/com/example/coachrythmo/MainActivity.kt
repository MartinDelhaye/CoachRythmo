package com.example.coachrythmo

import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.coachrythmo.data.source.AppDatabase
import com.example.coachrythmo.data.seed.SeedManager
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.presentation.components.CustomMenu
import com.example.coachrythmo.presentation.components.TodaySessionViewModel
import com.example.coachrythmo.presentation.home.HomeScreen
import com.example.coachrythmo.presentation.home.HomeViewModel
import com.example.coachrythmo.presentation.list.AddRoutineScreen
import com.example.coachrythmo.presentation.list.ListRoutinesScreen
import com.example.coachrythmo.presentation.list.ListRoutinesViewsModel
import com.example.coachrythmo.presentation.list.RoutineDetailScreen
import com.example.coachrythmo.presentation.session.SessionScreen
import com.example.coachrythmo.presentation.session.SessionViewModel
import com.example.coachrythmo.presentation.compte.CompteScreen
import com.example.coachrythmo.presentation.compte.CompteViewModel
import com.example.coachrythmo.presentation.map.MapPickerScreen
import com.example.coachrythmo.presentation.suivi.SuiviViewModel
import com.example.coachrythmo.presentation.suivi.SuiviScreen
import com.example.coachrythmo.ui.theme.CoachRythmoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    private val dev = true

    private fun startGeofenceService() {
        val intent = Intent(this, com.example.coachrythmo.location.GeofenceService::class.java)
        startForegroundService(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            startGeofenceService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Demande de permissions localisation
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        } else {
            startGeofenceService()
        }
        setContent {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    if (dev) {
                        try {
                            SeedManager.seedDatabase(
                                db.routineDao(),
                                db.exerciseDao(),
                                db.routineExerciseDao(),
                                db.sessionDao()
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            CoachRythmoTheme {
                val navController = rememberNavController()

                val listViewModel = viewModel<ListRoutinesViewsModel> {
                    ListRoutinesViewsModel(
                        dao = db.routineDao(),
                        exerciseDao = db.exerciseDao(),
                        routineExerciseDao = db.routineExerciseDao()
                    )
                }
                val homeViewModel = viewModel<HomeViewModel> {
                    HomeViewModel(db.routineDao(), db.sessionDao())
                }
                val todaySessionViewModel = viewModel<TodaySessionViewModel> {
                    TodaySessionViewModel(db.routineExerciseDao())
                }
                val suiviViewModel = viewModel<SuiviViewModel> {
                    SuiviViewModel(db.sessionDao())
                }
                val compteViewModel = viewModel<CompteViewModel> {
                    CompteViewModel()
                }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute?.startsWith("session_screen") != true &&
                            currentRoute?.startsWith("routine_detail") != true) {
                            CustomMenu(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(
                                navController,
                                homeViewModel,
                                todaySessionViewModel
                            )
                        }

                        composable(Screen.RoutinesListScreen.route) {
                            ListRoutinesScreen(navController, listViewModel)
                        }

                        composable(Screen.AddRoutineScreen.route) {
                            AddRoutineScreen(navController, listViewModel)
                        }

                        composable(
                            route = Screen.RoutineDetail.route,
                            arguments = listOf(navArgument("routineId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getInt("routineId") ?: return@composable
                            RoutineDetailScreen(navController, listViewModel, routineId)
                        }

                        composable(Screen.SuiviScreen.route) {
                            SuiviScreen(navController, suiviViewModel)
                        }

                        composable(Screen.CompteScreen.route) {
                            CompteScreen(navController, compteViewModel)
                        }

                        composable(Screen.SessionScreen.route) { backStackEntry ->
                            val routineId = backStackEntry.arguments
                                ?.getString("routineId")
                                ?.toIntOrNull()

                            val sessionViewModel = viewModel<SessionViewModel> {
                                SessionViewModel(
                                    routineDao = db.routineDao(),
                                    routineExerciseDao = db.routineExerciseDao(),
                                    sessionDao = db.sessionDao(),
                                    routineId = routineId
                                )
                            }
                            SessionScreen(sessionViewModel, navController)
                        }
                        composable(Screen.MapPickerScreen.route) {
                            val parentEntry = remember(it) {
                                navController.getBackStackEntry(Screen.AddRoutineScreen.route)
                            }
                            MapPickerScreen(
                                navController = navController,
                                onLocationPicked = { lat, lng ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("latitude", lat)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("longitude", lng)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}