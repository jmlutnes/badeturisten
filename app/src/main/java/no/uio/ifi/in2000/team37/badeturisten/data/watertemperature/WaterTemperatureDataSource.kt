package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.WaterTemperatureAPIResponse
import no.uio.ifi.in2000.team37.badeturisten.dependencyinjection.WaterTemperatureHttpClient
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WaterTemperatureDataSource(@WaterTemperatureHttpClient private val client: HttpClient) {
    suspend fun getData(daysOfMeasurements: Long): List<Tsery> {/*
        request to fetch data from june - august 2023
        val response = client.get("https://havvarsel-frost.met.no/api/v1/obs/badevann/get?incobs=true&time=2023-06-01T00%3A00%3A00Z%2F2023-08-01T23%3A59%3A59Z&nearest=%7B%22maxdist%22%3A10%2C%22maxcount%22%3A50%2C%22points%22%3A%5B%7B%22lon%22%3A10.74%2C%22lat%22%3A59.91%7D%5D%7D")
            .body<WaterTemperatureAPIResponse>()
        */
        val currentDate = LocalDate.now()
        val cutoffDate = LocalDate.now().minusDays(daysOfMeasurements)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val currentDateString = currentDate.format(formatter)
        val sevenDaysAgoString = cutoffDate.format(formatter)

        val currentDateAPIFormat = currentDateString + "T23%3A59%3A59Z"
        val sevenDaysAgoAPIFormat = sevenDaysAgoString + "T00%3A00%3A00Z%2F"

        val timeFrame = sevenDaysAgoAPIFormat + currentDateAPIFormat

        val response =
            client.get("https://havvarsel-frost.met.no/api/v1/obs/badevann/get?incobs=true&time=${timeFrame}&nearest=%7B%22maxdist%22%3A10%2C%22maxcount%22%3A50%2C%22points%22%3A%5B%7B%22lon%22%3A10.74%2C%22lat%22%3A59.91%7D%5D%7D")


        return if (response.status.value in 200..299) {
            val deserializedResponse = response.body<WaterTemperatureAPIResponse>()
            return deserializedResponse.data.tseries
        } else {
            listOf()
        }
    }
}