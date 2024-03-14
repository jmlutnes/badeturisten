package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
    val waterTemperatureUIState = homeViewModel.waterTemperatureState.collectAsState().value
    Column {
        Text(text = "Temp = $temp")

        LazyColumn {
            item {
                Log.v("home", "${waterTemperatureUIState.observations}")
                waterTemperatureUIState.observations.forEach {
                    Text("Name: ${it.header.extra.name}")
                    Text("Latitude: ${it.header.extra.pos.lat}")
                    Text("Longitude: ${it.header.extra.pos.lon}")
                    Text("Siste måling: ${it.observations[0].body.value}\n")
                }
            }
        }
    }

}