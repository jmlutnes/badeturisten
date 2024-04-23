package no.uio.ifi.in2000.team37.badeturisten

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomNavigationBar
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.theme.BadeturistenTheme
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BeachProfile
import no.uio.ifi.in2000.team37.badeturisten.ui.components.Screens
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
/*
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val beachViewModel: BeachViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BadeturistenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //BottomNavigationBar()
                    AppContent(/*homeViewModel, searchViewModel, beachViewModel, favouritesViewModel*/)
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(/*
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    beachViewModel: BeachViewModel,
    favouritesViewModel: FavouritesViewModel
*/) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {paddingValues ->
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
    }
}