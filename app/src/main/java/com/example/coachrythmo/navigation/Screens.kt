package com.example.coachrythmo.navigation;

sealed class Screen(val route: String) {
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object AddRoutineScreen : Screen(route = "add_routine_screen")
}