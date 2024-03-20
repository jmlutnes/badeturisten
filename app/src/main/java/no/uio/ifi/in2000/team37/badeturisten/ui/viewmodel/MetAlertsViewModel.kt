package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.WeatherWarning

data class MetAlertsUiState(val alerts: List<WeatherWarning>)

@RequiresApi(Build.VERSION_CODES.O)
class MetAlertsViewModel() : ViewModel() {

    private val metAlertsRepository = MetAlertsRepository(MetAlertsDataSource())

    var metAlertsUiState by mutableStateOf(MetAlertsUiState(alerts = listOf()))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val alerts = metAlertsRepository.getWeatherWarnings()
            metAlertsUiState = metAlertsUiState.copy(alerts = alerts)
        }
    }
}