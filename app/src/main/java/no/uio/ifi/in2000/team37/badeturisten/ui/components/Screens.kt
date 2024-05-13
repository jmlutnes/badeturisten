package no.uio.ifi.in2000.team37.badeturisten.ui.components

sealed class Screens(val route: String) {
    data object Home : Screens("homeScreen")
    data object Search : Screens("searchScreen")
    data object Favorite : Screens("favoritesScreen")
}