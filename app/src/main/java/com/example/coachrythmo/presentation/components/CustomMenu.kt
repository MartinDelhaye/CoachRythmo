package com.example.coachrythmo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.coachrythmo.R
import com.example.coachrythmo.navigation.Screen
import com.example.coachrythmo.ui.theme.CRDark
import com.example.coachrythmo.ui.theme.CRPrimaryRed
import com.example.coachrythmo.ui.theme.CRWhite


@Composable
fun CustomMenu(navController: NavController) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.RoutinesListScreen,
        Screen.SuiviScreen,
        Screen.CompteScreen
    )

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = CRDark,
                shape = RoundedCornerShape(50.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {

            items.forEach { screen ->
                val isSelected = currentRoute == screen.route
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = if (isSelected) CRPrimaryRed else CRWhite,
                            shape = CircleShape
                        )
                        .clickable() {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.HomeScreen.route)
                                launchSingleTop = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = when(screen) {
                            Screen.HomeScreen -> painterResource(id = R.drawable.ic_home)
                            Screen.RoutinesListScreen -> painterResource(id = R.drawable.icon_routine)
                            Screen.SuiviScreen -> painterResource(id = R.drawable.icon_suivi)
                            Screen.CompteScreen -> painterResource(id = R.drawable.icon_compte)
                                               },
                        contentDescription = screen.route,
                        modifier = Modifier.size(25.dp),
                        tint = if (isSelected) CRWhite else CRDark
                        )
                }
            }
        }
    }
}
