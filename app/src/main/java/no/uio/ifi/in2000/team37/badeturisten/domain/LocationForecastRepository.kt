package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import no.uio.ifi.in2000.team37.badeturisten.model.locationforecast.ForecastNextHour

interface LocationForecastRepository {
    //val dataSource: LocationForecastDataSource
    suspend fun loadForecastNextHour()
    fun observeForecastNextHour(): StateFlow<ForecastNextHour?>
}