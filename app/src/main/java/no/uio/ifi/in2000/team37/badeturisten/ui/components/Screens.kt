package no.uio.ifi.in2000.team37.badeturisten.ui.components

sealed class Screens(val route: String) {
    object Home : Screens("homeScreen")
    object Search : Screens("searchScreen")
    object Favorite : Screens("favoritesScreen")
}