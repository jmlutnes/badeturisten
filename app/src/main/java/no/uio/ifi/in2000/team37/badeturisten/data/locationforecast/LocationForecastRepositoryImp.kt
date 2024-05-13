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

    override suspend fun loadForecastNextHour() {
        val result = datasource.getForecastData()

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