package no.uio.ifi.in2000.team37.badeturisten.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.HomeViewModel
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.WaterTempViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeachProfile(homeViewModel : HomeViewModel = viewModel(), waterTempViewModel : WaterTempViewModel = viewModel(), navController : NavController, beachName : String?) {
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
    val waterTemperatureUIState = waterTempViewModel.waterTemperatureState.collectAsState().value
    homeViewModel.fetchBeach(beachName)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Badested") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)){
            //Text()


        }

    }


}
