package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts.Feature
import no.uio.ifi.in2000.team37.badeturisten.domain.MetAlertsRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MetAlertsRepositoryImp(
    private val datasource: MetAlertsDataSource,
) : MetAlertsRepository {

    private val metAlertsObservations = MutableStateFlow<List<WeatherWarning>>(listOf())
    override fun getMetAlertsObservations() = metAlertsObservations.asStateFlow()

    override suspend fun getWeatherWarnings() {
        val result = datasource.getData()
        val featuresArray = result.features

        metAlertsObservations.update {
            featuresArray.mapNotNull { feature ->
                val endTimeStr = feature.whenX.interval[1]
                val currentTimeStr = LocalDateTime.now(ZoneId.systemDefault()).withNano(0)
                if (calculateStatus(endTimeStr, currentTimeStr) == "Aktiv" && feature.properties.county.contains("03")) {
                    createWeatherWarning(feature, endTimeStr)
                } else null
            }.distinctBy { it.area + it.event + it.description }
        }
    }


    override fun calculateStatus(eventEndingTime: String?, currentTime: LocalDateTime): String {
        val endTime = LocalDateTime.parse(eventEndingTime, DateTimeFormatter.ISO_DATE_TIME)
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