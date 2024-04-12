package no.uio.ifi.in2000.team37.badeturisten.ui.components

import android.graphics.Color.parseColor
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.network.NetworkUtils.isNetworkAvail

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomBar(navController: NavController) {
    val context = LocalContext.current
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
                IconButton(onClick = {
                    if (isNetworkAvail(context)) {
                        navController.navigate("homeScreen")
                } else {
                        Toast.makeText(context, "Nettverk er nødvendig for å søke.", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
                IconButton(
                    onClick = {
                        if (isNetworkAvail(context)) {
                            navController.navigate("favoritesScreen")
                        } else {
                            Toast.makeText(context, "Nettverk er nødvendig for å søke.", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Favorite"
                    )
                }
                IconButton(
                    onClick = {
                        if (isNetworkAvail(context)) {
                            navController.navigate("searchScreen")
                        } else {
                            Toast.makeText(context, "Nettverk er nødvendig for å søke.", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            }
        }
    }
}