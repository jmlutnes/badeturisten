package no.uio.ifi.in2000.team37.badeturisten.ui.components

import android.graphics.Color.parseColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.FavouritesScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.HomeScreen
import no.uio.ifi.in2000.team37.badeturisten.ui.screen.SearchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBar(navController: NavController) {
    Box {
        BottomAppBar(
            containerColor = Color(parseColor("#a9c7ee")),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { navController.navigate("homeScreen") }) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = { navController.navigate("favoritesScreen") }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorite"
                    )
                }
                IconButton(onClick = { navController.navigate("searchScreen") }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            }
        }
    }
}