package no.uio.ifi.in2000.team37.badeturisten.ui.favorites

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.BeachCard

@Composable
fun FavoritesScreen(
    navController: NavController,
) {
    val favouritesViewModel: FavoritesViewModel = hiltViewModel()

    val favouritesState = favouritesViewModel.favouritesState.collectAsState().value
    val beachInfo = favouritesViewModel.beachDetails.collectAsState().value

    val state = rememberLazyGridState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Spacer(Modifier.height(50.dp))
                Text(
                    text = "Favoritter",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("favouriteScreen", "${favouritesState.favourites.isEmpty()}")
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        if (favouritesState.favourites.isEmpty()) {
                            val inlineContent = (mapOf("heartIcon" to InlineTextContent(
                                placeholder = Placeholder(
                                    width = 1.em,
                                    height = 1.em,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Heart"
                                )
                            }))
                            Text(
                                text = buildAnnotatedString {
                                    append("Legg til dine favoritter ved å trykke på hjerteikonet (")
                                    appendInlineContent("heartIcon", "[heartIcon]")
                                    append(") i profilen til et badested!")
                                },
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp),
                                inlineContent = inlineContent
                            )
                        } else {
                            LazyVerticalGrid(
                                state = state,
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Adaptive(180.dp),
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(favouritesState.favourites) { beach ->
                                    BeachCard(
                                        beach = beach, 0, navController = navController, beachInfo
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}