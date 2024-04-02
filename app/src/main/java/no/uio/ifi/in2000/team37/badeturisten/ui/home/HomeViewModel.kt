package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.OsloKommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour

data class kommuneBeachList(val beachList: List<Beach> = listOf())

data class ForecastUIState(
    val forecastNextHour: ForecastNextHour? = null
)

class HomeViewModel(): ViewModel() {

    //REPOSITORIES
    private val locationForecastRepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())
    private val osloKommuneRepository = OsloKommuneRepository (
        OsloKommuneDatasource()
    )

    //STATEFLOW
    private val _kommuneBeachList = MutableStateFlow(kommuneBeachList())

    val kommuneBeachList: StateFlow<kommuneBeachList> = _kommuneBeachList.asStateFlow()
    val forecastState: StateFlow<ForecastUIState> = locationForecastRepository.observeForecastNextHour()
        .map { ForecastUIState(forecastNextHour = it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ForecastUIState()
        )

    init {
        viewModelScope.launch {
            locationForecastRepository.loadForecastNextHour()
            _kommuneBeachList.update{
                kommuneBeachList(osloKommuneRepository.makeBeaches(0.0,0.0))
            }
        }
    }
}
