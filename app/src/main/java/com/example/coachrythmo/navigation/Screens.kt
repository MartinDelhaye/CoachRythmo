package com.example.coachrythmo.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
<<<<<<< HEAD
    data object AddRoutineScreen : Screen(route = "add_routine_screen")
=======
    data object SuiviScreen : Screen("suivi")
    data object CompteScreen : Screen("compte")
>>>>>>> 3e4fad36a7169813c7d46ee05c6e387674a74330
}