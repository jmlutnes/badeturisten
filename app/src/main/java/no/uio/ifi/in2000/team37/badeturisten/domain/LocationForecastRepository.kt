package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.ForecastNextHour

interface LocationForecastRepository {
    suspend fun loadForecastNextHour()
    fun observeForecastNextHour(): StateFlow<ForecastNextHour?>
}