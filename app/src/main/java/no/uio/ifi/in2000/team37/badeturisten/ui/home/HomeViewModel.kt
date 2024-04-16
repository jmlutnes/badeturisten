package no.uio.ifi.in2000.team37.badeturisten.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.model.WeatherWarning

import javax.inject.Inject

data class MetAlertsUIState(
    val alerts: List<WeatherWarning> = listOf()
)

data class ForecastUIState(
    val forecastNextHour: ForecastNextHour? = null
)

data class BeachesUIState (
    val beaches: List<Beach> = listOf()
)


@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel @Inject constructor(
    private val _locationForecastRepository: LocationForecastRepositoryImp,
    private val _osloKommuneRepository: OsloKommuneRepositoryImp,
    private val _beachesRepository: BeachRepositoryImp,
    private val _metAlertsRepository: MetAlertsRepositoryImp
): ViewModel() {
    //henter vaer melding
    val forecastState: StateFlow<ForecastUIState> = _locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    //henter strender
    var beachList: List<Beach> = listOf()

    fun reloadBeaches() {
        viewModelScope.launch {
            launch {
                beachList = CombineBeachesUseCase(_beachesRepository, _osloKommuneRepository).invoke()
            }
        }
    }

    //henter farevarsler
    val metAlertsState: StateFlow<MetAlertsUIState> = _metAlertsRepository.getMetAlertsObservations()
        .map { MetAlertsUIState(alerts = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MetAlertsUIState()
        )

    init {
        viewModelScope.launch {
            _locationForecastRepository.loadForecastNextHour()
            _beachesRepository.loadBeaches()
            _metAlertsRepository.getWeatherWarnings()

            beachState.update {
                BeachesUIState(CombineBeachesUseCase(_beachesRepository, _osloKommuneRepository)())
            }
        }
    }
}
