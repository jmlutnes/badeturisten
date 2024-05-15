package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
            NavigationBarItem(selected = navigationItem.route == currentDestination?.route,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon, contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    if (navController.currentDestination?.route.equals("beachProfile/{beachName}")) {
                        if (navigationItem.route == (navController.previousBackStackEntry?.destination?.route
                                ?: "")
                        ) {
                            navController.popBackStack()
                            return@NavigationBarItem
                        }
                    }
                    navController.navigate(navigationItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
