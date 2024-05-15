package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.domain.LocationForecastRepository
import javax.inject.Inject

class LocationForecastRepositoryImp @Inject constructor(
    private val datasource: LocationForecastDataSource,
) : LocationForecastRepository {

    private val forecastNextHour = MutableStateFlow<ForecastNextHour?>(null)

    /**
     * Fetches the forecast for the next hour for Oslo from the datasource. Coordinates can be changed
     * to get data for a different location, but the app currently is only meant for the Oslo area.
     */
    override suspend fun loadForecastNextHour() {
        val lat = 59.91276
        val lon = 10.74608
        val result = datasource.getForecastData(lat, lon)

        if (result != null) {
            val temp = result.properties.timeseries[0].data.instant.details.airTemperature
            val precipitation =
                result.properties.timeseries[0].data.next1Hours.details.precipitationAmount
            val symbolCode = result.properties.timeseries[0].data.next1Hours.summary.symbolCode
            val forecast = ForecastNextHour(temp, precipitation, symbolCode)

            forecastNextHour.update {
                forecast
            }
        }
    }

    override fun observeForecastNextHour(): StateFlow<ForecastNextHour?> =
        forecastNextHour.asStateFlow()
}