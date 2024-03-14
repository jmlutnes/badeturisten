package no.uio.ifi.in2000.team37.badeturisten.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastRepository

data class TemperatureLocationForecast(val temp: Double? = null)

class HomeViewModel: ViewModel() {
    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(
        TemperatureLocationForecast()
    )

    var LocationForecastrepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())


    init {
        viewModelScope.launch {
            val forecastResult = TemperatureLocationForecast(LocationForecastrepository.getTemperature())
            _locationTemperature.update { forecastResult }
        }
    }

}