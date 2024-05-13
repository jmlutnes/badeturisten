package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = "",
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Hjem", icon = Icons.Filled.Home, route = Screens.Home.route
            ),
            BottomNavigationItem(
                label = "Favoritter", icon = Icons.Filled.Favorite, route = Screens.Favorite.route
            ),
            BottomNavigationItem(
                label = "Søk", icon = Icons.Filled.Search, route = Screens.Search.route
            ),
        )
    }
}