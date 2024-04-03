package no.uio.ifi.in2000.team37.badeturisten.ui.favourites

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.components.beachCard

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavouritesScreen(
    favouritesViewModel: FavouritesViewModel = viewModel(),
    navController: NavController
) {
    val favouritesState = favouritesViewModel.favouritesState.collectAsState().value


    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    ) {
        items(favouritesState.favourites) { beach ->
            Log.d("FavoScreen, cards", "$beach")
            beachCard(beach = beach, navController = navController)
        }
    }
}