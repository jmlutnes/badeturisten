package no.uio.ifi.in2000.team37.badeturisten.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.team37.badeturisten.model.Beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.ui.watertemperature.WaterTempViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel(), waterTempViewModel: WaterTempViewModel = viewModel()) {
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
    val waterTemperatureUIState = waterTempViewModel.waterTemperatureState.collectAsState().value
    Column {
        Text(text = "Temp = $temp")

        LazyColumn{
            items(waterTemperatureUIState.beaches) {beach ->
                beachCard(beach = beach)
            }

        }

    }
    // meny i top bar/venstre oevre hjoerne
    //luft temp
    //vear ikon
    //varsel knapp/melding
    //strand kort (near deg)
}

@Composable
fun beachCard(beach: Beach) {
    Card {
        Text(text = beach.name)
        //luft temp
        //navn
        //bilde
        //favoritt
        // statistikk
        // anmeldelser    
    }
    
}