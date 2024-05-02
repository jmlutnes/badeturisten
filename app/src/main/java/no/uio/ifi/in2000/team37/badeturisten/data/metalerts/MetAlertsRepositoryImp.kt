package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts.Feature
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

        metAlertsObservations.update {
            featuresArray.mapNotNull { feature ->
                val endTimeStr = feature.whenX.interval[1]
                if (
                    calculateStatus(endTimeStr) == "Aktiv" &&
                    feature.properties.county.contains("03")) {
                    createWeatherWarning(feature, endTimeStr)
                } else null
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun calculateStatus(eventEndingTime: String?): String {
        val endTime = LocalDateTime.parse(eventEndingTime, DateTimeFormatter.ISO_DATE_TIME)
        val currentTime = LocalDateTime.now(ZoneId.systemDefault()).withNano(0)
        return if (endTime.isAfter(currentTime)) "Aktiv" else "Inaktiv"
    }

    private fun createWeatherWarning(feature: Feature, endTimeStr: String): WeatherWarning {
        return feature.properties.let { prop ->
            WeatherWarning(
                area = prop.area,
                description = prop.description,
                event = prop.event,
                severity = prop.severity,
                instruction = prop.instruction,
                eventEndingTime = endTimeStr,
                status = "Aktiv",
                web = prop.web
            )
        }
    }
}