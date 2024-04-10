package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepository
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour
import no.uio.ifi.in2000.team37.badeturisten.network.NetworkUtils

data class MetAlertsUIState(
    val alerts: List<WeatherWarning> = listOf()
)

data class ForecastUIState(
    val forecastNextHour: ForecastNextHour? = null
)



@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _networkAvailable = MutableStateFlow<Boolean>(false)
    val networkAvailable: StateFlow<Boolean> = _networkAvailable.asStateFlow()


    //henter vaer melding
    private val _locationForecastRepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())

    val forecastState: StateFlow<ForecastUIState> = _locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    private fun checkNetworkAvailability() {
        _networkAvailable.value = NetworkUtils.isNetworkAvail(getApplication())
    }

    //henter strender
    private val _osloKommuneRepository = OsloKommuneRepository()
    private val _beachesRepository = BeachRepository()

    var beachList: List<Beach> = listOf()

    fun reloadBeaches() {
        if (_networkAvailable.value) {
            viewModelScope.launch {
                launch {
                    beachList = CombineBeachesUseCase(_beachesRepository, _osloKommuneRepository).invoke()
                }
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
            checkNetworkAvailability()
            _locationForecastRepository.loadForecastNextHour()
            _beachesRepository.loadBeaches(application)
            _metAlertsRepository.getWeatherWarnings()

            beachList = CombineBeachesUseCase(_beachesRepository, _osloKommuneRepository).invoke()
        }
    }
}
