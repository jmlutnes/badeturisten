package no.uio.ifi.in2000.team37.badeturisten.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    // meny i top bar/venstre oevre hjoerne
    //luft temp
    //vear ikon
    //varsel knapp/melding
    //strand kort (near deg)
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
    val waterTemperatureUIState = homeViewModel.waterTemperatureState.collectAsState().value
    Column {
        Text(text = "Temp = $temp")

        LazyColumn {
            item {
                waterTemperatureUIState.observations.forEach {
                    Text("Name: ${it.header.extra.name}")
                    Text("Latitude: ${it.header.extra.pos.lat}")
                    Text("Longitude: ${it.header.extra.pos.lon}\n")
                }
            }
        }
    }
}