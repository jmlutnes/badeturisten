package no.uio.ifi.in2000.team37.badeturisten.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val temp = homeViewModel._locationTemperature.collectAsState().value.temp
    Column {
        Text(text = "Temp = $temp")
    }
}