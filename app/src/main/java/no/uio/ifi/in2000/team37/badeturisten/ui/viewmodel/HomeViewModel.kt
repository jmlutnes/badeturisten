package no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.LocationForecast.LocationForecastRepository
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository

data class TemperatureLocationForecast(val temp: Double? = null)

class HomeViewModel(): ViewModel() {
    private val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository(
        WaterTemperatureDataSource()
    )


    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(
        TemperatureLocationForecast()
    )

    var LocationForecastrepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())


    init {
        viewModelScope.launch (Dispatchers.IO) {
            val forecastResult = TemperatureLocationForecast(LocationForecastrepository.getTemperature())
            _locationTemperature.update { forecastResult }
        }
    }
}