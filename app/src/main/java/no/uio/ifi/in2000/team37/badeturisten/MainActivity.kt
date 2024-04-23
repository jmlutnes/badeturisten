package no.uio.ifi.in2000.team37.badeturisten

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BottomNavigationBar
import no.uio.ifi.in2000.team37.badeturisten.ui.favourites.FavouritesViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.home.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.search.SearchViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.theme.BadeturistenTheme
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.BeachViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val beachViewModel: BeachViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()

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
                    AppContent(homeViewModel, searchViewModel, beachViewModel, favouritesViewModel)
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppContent(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    beachViewModel: BeachViewModel,
    favouritesViewModel: FavouritesViewModel
) {
    val navController = rememberNavController()
    BottomNavigationBar(navController) // Example placeholder for your navigation bar
/*
    //gjoer dette inni screen
    // Suppose you want to display data from HomeViewModel
    val forecastState = homeViewModel.forecastState.collectAsState().value.forecastNextHour
    val beachState = homeViewModel.beachState.collectAsState().value
    val alertState = homeViewModel.metAlertsState.collectAsState().value
    val beachinfo = homeViewModel.beachDetails.collectAsState().value
    Text(text = "Current weather: ${forecastState?.temp}°")
*/
}