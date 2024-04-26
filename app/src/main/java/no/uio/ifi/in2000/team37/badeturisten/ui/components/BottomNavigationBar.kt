package no.uio.ifi.in2000.team37.badeturisten.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BeachProfile
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
            NavigationBarItem(
                selected = navigationItem.route == currentDestination?.route,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    if (navController.currentDestination?.route.equals("beachProfile/{beachName}")) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
    /*Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(
                route = "beachProfile/{beachName}",
                arguments = listOf(navArgument("beachName") { type = NavType.StringType })
            ) { backStackEntry ->
                val beachName = backStackEntry.arguments?.getString("beachName")
                BeachProfile(navController = navController, beachName = beachName)
            }

            composable(route = "homeScreen") {
                HomeScreen(navController = navController)
            }

            composable(route = "favoritesScreen") {
                FavouritesScreen(navController = navController)
            }
            composable(route = "searchScreen") {
                SearchScreen(navController = navController)
            }
        }
    }*/
}
