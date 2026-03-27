package com.example.coachrythmo.navigation;

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object SuiviScreen : Screen("suivi")
    data object CompteScreen : Screen("compte")
}