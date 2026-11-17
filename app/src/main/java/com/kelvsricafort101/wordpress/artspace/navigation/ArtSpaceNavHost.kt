package com.kelvsricafort101.wordpress.artspace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kelvsricafort101.wordpress.artspace.ui.screens.AboutAppDestination
import com.kelvsricafort101.wordpress.artspace.ui.screens.AboutScreen
import com.kelvsricafort101.wordpress.artspace.ui.screens.HomeDestination
import com.kelvsricafort101.wordpress.artspace.ui.screens.HomeScreen

/**
 * Provides Navigation Graph for the application.
 */
@Composable
fun ArtSpaceNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ArtSpaceNavigationDrawer(
        selectedRoute = currentRoute,
        onNavigate = { route ->
            navController.navigate(route) {
                popUpTo(HomeDestination.route) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }
    ) { openDrawer ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = modifier
        ) {
            composable(route = HomeDestination.route) {
                HomeScreen(
                    openDrawer = openDrawer,
                    modifier = modifier
                )
            }
            composable(route = AboutAppDestination.route) {
                AboutScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}