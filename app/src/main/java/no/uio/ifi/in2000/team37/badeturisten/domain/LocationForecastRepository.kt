package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.ForecastNextHour

interface LocationForecastRepository {

    /**
     * Gets information of the forecast the next hour from LocationForecastDataSource,
     * and filters it and updates the flow
     */
    suspend fun loadForecastNextHour()

    /**
     * Fetch an information flow of the weather forecast the next hour
     */
    fun observeForecastNextHour(): StateFlow<ForecastNextHour?>
}