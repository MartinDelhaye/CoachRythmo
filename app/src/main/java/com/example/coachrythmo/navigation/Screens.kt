package com.example.coachrythmo.navigation

sealed class Screen(val route: String) {
    data object Login : Screen(route = "login")
    data object Verify : Screen(route = "verify")
    data object HomeScreen : Screen(route = "home")
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object AddRoutineScreen : Screen(route = "add_routine_screen")
    data object SuiviScreen : Screen("suivi")
    data object CompteScreen : Screen("compte")
}

