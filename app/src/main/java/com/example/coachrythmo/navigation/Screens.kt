package com.example.coachrythmo.navigation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen(route = "home")
    data object RoutinesListScreen : Screen(route = "routines_list_screen")
    data object AddRoutineScreen : Screen(route = "add_routine_screen")
    data object SuiviScreen : Screen("suivi")
    data object CompteScreen : Screen("compte")
    data object RoutineDetail : Screen(route = "routine_detail/{routineId}") {
        fun createRoute(routineId: Int) = "routine_detail/$routineId"
    }
    data object SessionScreen : Screen(route = "session_screen/{routineId}") {
        fun createRoute(routineId: Int?) = "session_screen/$routineId"
    }
    data object MapPickerScreen : Screen(route = "map_picker")
}