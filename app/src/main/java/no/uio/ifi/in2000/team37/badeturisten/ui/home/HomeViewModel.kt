package no.uio.ifi.in2000.team37.badeturisten.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository
import no.uio.ifi.in2000.team37.badeturisten.model.Beach.Beach

data class TemperatureLocationForecast(val temp: Double? = null)

data class WaterTemperatureUIState (
    val observations: List<Beach> = listOf()
)

class HomeViewModel: ViewModel() {

    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(TemperatureLocationForecast())

    var repository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())

    val waterTemperatureRepository = WaterTemperatureRepository(WaterTemperatureDataSource())

    val waterTemperatureState: StateFlow<WaterTemperatureUIState> = waterTemperatureRepository.getObservations()
    .map { WaterTemperatureUIState(observations = it) }
    .stateIn(
    viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = WaterTemperatureUIState()
    )

    init {
        viewModelScope.launch {
            val result = TemperatureLocationForecast(repository.getTemperature())
            _locationTemperature.update { result }
            waterTemperatureRepository.loadBeaches()
        }
    }

}