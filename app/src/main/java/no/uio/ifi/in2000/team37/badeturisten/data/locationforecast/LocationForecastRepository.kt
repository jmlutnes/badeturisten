package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour

class LocationForecastRepository(val dataSource: LocationForecastDataSource) {

    private val forecastNextHour = MutableStateFlow<ForecastNextHour?>(null)

    suspend fun loadForecastNextHour() {
        val result = dataSource.getForecastData()

        if (result != null) {
            val temp = result.properties.timeseries[0].data.instant.details.airTemperature
            val precipitation = result.properties.timeseries[0].data.next1Hours.details.precipitationAmount
            val symbolCode = result.properties.timeseries[0].data.next1Hours.summary.symbolCode
            val forecast = ForecastNextHour(temp, precipitation, symbolCode)

            forecastNextHour.update {
                forecast
            }
        }
    }
    fun observeForecastNextHour(): StateFlow<ForecastNextHour?> = forecastNextHour.asStateFlow()
}