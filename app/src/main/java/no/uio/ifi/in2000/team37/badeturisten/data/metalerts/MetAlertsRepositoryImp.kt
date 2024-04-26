package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MetAlertsRepositoryImp(
    private val datasource: MetAlertsDataSource
): MetAlertsRepository{

    //lager en flow av MetAlerts
    private val metAlertsObservations = MutableStateFlow<List<WeatherWarning>>(listOf())
    override fun getMetAlertsObservations() = metAlertsObservations.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherWarnings() {
        val result = datasource.getData()
        val featuresArray = result.features

        //oppdaterer flow
        metAlertsObservations.update {
            featuresArray.mapNotNull { feature ->
                feature.properties.let { prop ->
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
    override fun calculateStatus(eventEndingTime: String?): String {
        eventEndingTime ?: return "Active"

        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val endTime = LocalDateTime.parse(eventEndingTime, formatter)
            val currentTime = LocalDateTime.now(ZoneId.systemDefault())

            if (endTime.isAfter(currentTime)) {
                "Aktiv"
            } else {
                "Ikke aktiv"
            }
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            "Feil"
        }
    }
}