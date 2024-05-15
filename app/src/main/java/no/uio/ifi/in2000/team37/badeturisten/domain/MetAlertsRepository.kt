package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.flow.StateFlow
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.WeatherWarning
import java.time.LocalDateTime

interface MetAlertsRepository {

    /**
     * Fetch an information flow of weather warnings
     */
    fun getMetAlertsObservations(): StateFlow<List<WeatherWarning>>

    /**
     * Fetches weather warnings from datasource and updates the flow containing weather warnings
     */
    suspend fun getWeatherWarnings()

    /**
     * Calculate the active/non-active status of a weather warning
     */
    fun calculateStatus(eventEndingTime: String?, currentTime: LocalDateTime): String
}
