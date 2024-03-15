package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature.Tsery
import no.uio.ifi.in2000.team37.badeturisten.model.watertemperature.WaterTemperatureAPIResponse

class WaterTemperatureDataSource {
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson{}
        }
    }

    suspend fun getData(latitude: Double, longitude: Double, maxDistance: Int, maxResultCount: Int): List<Tsery> {
        val timeFrame = "2023-06-01T00:00:00Z/2023-08-01T23:59:59Z"
        // val response = client.get("https://havvarsel-frost.met.no/api/v1/obs/badevann/get?incobs=true&time=latest&nearest=%7B%22maxdist%22%3A${maxDistance}%2C%22maxcount%22%3A${maxResultCount}%2C%22points%22%3A%5B%7B%22lon%22%3A${longitude}%2C%22lat%22%3A${latitude}%7D%5D%7D")
        //    .body<FrostJsonResponse>()

        val response = client.get("https://havvarsel-frost.met.no/api/v1/obs/badevann/get?incobs=true&time=2023-06-01T00%3A00%3A00Z%2F2023-08-01T23%3A59%3A59Z&nearest=%7B%22maxdist%22%3A10%2C%22maxcount%22%3A50%2C%22points%22%3A%5B%7B%22lon%22%3A10.74%2C%22lat%22%3A59.91%7D%5D%7D")
            .body<WaterTemperatureAPIResponse>()

        return response.data.tseries
    }
}