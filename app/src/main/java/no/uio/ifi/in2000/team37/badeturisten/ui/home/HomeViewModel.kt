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
import no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast.LocationForecastData
import no.uio.ifi.in2000.team37.badeturisten.model.watertemperature.Tsery

data class TemperatureLocationForecast(val temp: Double? = null)

class HomeViewModel: ViewModel() {
    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(TemperatureLocationForecast())

    var LocationForecastrepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())


    init {
        viewModelScope.launch {
            val forecastResult = TemperatureLocationForecast(LocationForecastrepository.getTemperature())
            _locationTemperature.update { forecastResult }
        }
    }

}