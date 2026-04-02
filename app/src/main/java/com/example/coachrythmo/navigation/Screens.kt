package com.example.coachrythmo.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object AddRoutineScreen : Screen(route = "add_routine_screen")
    data object RoutineDetailScreen : Screen(route = "routine_detail_screen/{routineId}") {
        fun createRoute(routineId: Int) = "routine_detail_screen/$routineId"
    }
    data object SuiviScreen : Screen("suivi")
    data object CompteScreen : Screen("compte")

    data object StartRoutineScreen : Screen(route = "start_routine_screen/{routineId}") {
        fun createRoute(routineId: Int) = "start_routine_screen/$routineId"
    }
}