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
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureRepository

data class TemperatureLocationForecast(val temp: Double? = null)

class HomeViewModel(savedStateHandle : SavedStateHandle): ViewModel() {
    private val beachName : String = checkNotNull(savedStateHandle["beachName"])
    private val waterTempRepository : WaterTemperatureRepository = WaterTemperatureRepository()


    var _locationTemperature = MutableStateFlow<TemperatureLocationForecast>(
        TemperatureLocationForecast()
    )

    var LocationForecastrepository : LocationForecastRepository = LocationForecastRepository(dataSource = LocationForecastDataSource())


    init {
        viewModelScope.launch (Dispatchers.IO) {
            val forecastResult = TemperatureLocationForecast(LocationForecastrepository.getTemperature())
            val beach = waterTempRepository.loadBeach(beachName)
            _locationTemperature.update { forecastResult }
        }
    }

    fun fetchBeach(beachname: String?) {
        viewModelScope.launch (Dispatchers.IO) {
            waterTempRepository.loadBeach(beachname)
        }
    }
}