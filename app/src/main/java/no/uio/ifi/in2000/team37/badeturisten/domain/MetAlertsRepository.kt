package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.model.WeatherWarning

interface MetAlertsRepository {
    val dataSource: MetAlertsDataSource
    fun getMetAlertsObservations(): StateFlow<List<WeatherWarning>>

    suspend fun getWeatherWarnings()
    fun calculateStatus(eventEndingTime: String?): String
}
