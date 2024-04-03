package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.OsloKommuneRepository
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

data class kommuneBeachList(
    val beachList: List<Beach> = listOf()
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
    private val osloKommuneRepository = OsloKommuneRepository()
    private val _kommuneBeachList = MutableStateFlow(kommuneBeachList())

    val kommuneBeachList: StateFlow<kommuneBeachList> = _kommuneBeachList.asStateFlow()

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
            _kommuneBeachList.update{
                kommuneBeachList(osloKommuneRepository.makeBeaches(0.0,0.0))
            }
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