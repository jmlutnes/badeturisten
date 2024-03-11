package no.uio.ifi.in2000.team37.badeturisten.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast.LocationForecastData

data class TemperatureLocationForecast(val temp: Double? = null)

class HomeViewModel: ViewModel() {

    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(TemperatureLocationForecast())

    var repository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())

    init {
        viewModelScope.launch {
            val result = TemperatureLocationForecast(repository.getTemperature())
            _locationTemperature.update { result }
        }
    }

}