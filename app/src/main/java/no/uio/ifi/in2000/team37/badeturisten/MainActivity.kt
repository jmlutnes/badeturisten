package no.uio.ifi.in2000.team37.badeturisten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.BeachProfile
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.SearchScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.theme.BadeturistenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BadeturistenTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavScreen()
                }
            }
        }
    }
}

@Composable
fun NavScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "homeScreen") {
        composable(route = "homeScreen") {
            HomeScreen(navController = navController)
        }
        composable(
            route = "beachProfile/{beachName}",
            arguments = listOf(navArgument("beachName") { type = NavType.StringType })
        ) { backStackEntry ->
            val beachName = backStackEntry.arguments?.getString("beachName")
            BeachProfile(navController = navController, beachName = beachName)
        }
        composable(route = "favoritesScreen") {
            FavouritesScreen(navController = navController)
        }
        composable(route = "homeScreen") {
            SearchScreen(navController = navController)
        }
    }
}
