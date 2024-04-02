package no.uio.ifi.in2000.team37.badeturisten.ui.components

sealed class Screens(val route : String) {
    object Home : Screens("home_screen")
    object Search : Screens("search_screen")
    object Profile : Screens("profile_screen")
}