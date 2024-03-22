package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class WeatherWarning(
    val area: String,
    val description: String,
    val event: String,
    val severity: String,
    val instruction: String?,
    val eventEndingTime: String?,
    val status: String?,
    val web: String?
)

class MetAlertsRepository(private val dataSource: MetAlertsDataSource) {

    //lager en flow av MetAlerts
    private val metAlertsObservations = MutableStateFlow<List<WeatherWarning>>(listOf())
    fun getMetAlertsObservations() = metAlertsObservations.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeatherWarnings() {
        val result = dataSource.getData()
        val featuresArray = result.features

        //oppdaterer flow
        metAlertsObservations.update {
            featuresArray.mapNotNull { feature ->
                feature.properties?.let { prop ->
                    if (prop.county.contains("03")) {
                        WeatherWarning(
                            area = prop.area,
                            description = prop.description,
                            event = prop.event,
                            severity = prop.severity,
                            instruction = prop.instruction,
                            eventEndingTime = prop.eventEndingTime,
                            status = calculateStatus(prop.eventEndingTime),
                            web = prop.web
                        )
                    } else {
                        null
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateStatus(eventEndingTime: String?): String {
        eventEndingTime ?: return "Active"

        try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val endTime = LocalDateTime.parse(eventEndingTime, formatter)
            val currentTime = LocalDateTime.now(ZoneId.systemDefault())

            return if (endTime.isAfter(currentTime)) {
                "Aktiv"
            } else {
                "Ikke aktiv"
            }
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            return "Feil"
        }
    }
}