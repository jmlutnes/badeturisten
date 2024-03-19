package no.uio.ifi.in2000.team37.badeturisten.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.SearchScreen

@Composable
fun BottomBar(navController : NavHostController){
    var content by remember { mutableStateOf<@Composable() () -> Unit>({ HomeScreen(navController = navController) }) }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { content = { HomeScreen(navController = navController) } }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { content = { FavouritesScreen(navController = navController) } }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                        )
                    }
                    IconButton(onClick = { content = { SearchScreen(navController = navController) } }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}