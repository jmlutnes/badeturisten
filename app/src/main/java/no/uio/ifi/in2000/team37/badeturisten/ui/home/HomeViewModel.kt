package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.MetAlerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour

data class MetAlertsUIState(
    val alerts: List<WeatherWarning> = listOf()
)
data class BeachesUIState (
    val beaches: List<Beach> = listOf()
)
data class ForecastUIState(
    val forecastNextHour: ForecastNextHour? = null
)

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel: ViewModel() {

    //henter vaer melding
    private val _locationForecastRepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())
    val forecastState: StateFlow<ForecastUIState> = _locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    init {
        viewModelScope.launch {
            _locationForecastRepository.loadForecastNextHour()
        }
    }

    //henter strender
    //trenger en annen maate aa hente alle strender paa
    private val _beachesRepository = BeachRepository()
    val beachesState: StateFlow<BeachesUIState> = _beachesRepository.getBeachObservations()
        .map { BeachesUIState(beaches = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BeachesUIState()
        )

    init {
        viewModelScope.launch {
            _beachesRepository.loadBeaches()
        }
    }

    //henter farevarsler
    private val _metAlertsRepository = MetAlertsRepository(MetAlertsDataSource())
    val metAlertsState: StateFlow<MetAlertsUIState> = _metAlertsRepository.getMetAlertsObservations()
        .map { MetAlertsUIState(alerts = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MetAlertsUIState()
        )

    init {
        viewModelScope.launch {
            _metAlertsRepository.getWeatherWarnings()
        }
    }
}