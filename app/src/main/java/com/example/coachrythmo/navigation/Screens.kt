package com.example.coachrythmo.navigation;

sealed class Screen(val route: String) {
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
}