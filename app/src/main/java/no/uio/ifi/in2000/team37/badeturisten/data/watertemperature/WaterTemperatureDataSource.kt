package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature.FrostJsonResponse
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature.Tsery

class WaterTemperatureDataSource {
    private val client = HttpClient() {
        install(ContentNegotiation) {
            gson{}
        }
    }

    suspend fun getData(latitude: Double, longitude: Double, maxDistance: Int, maxResultCount: Int): List<Tsery> {
        val response = client.get("https://havvarsel-frost.met.no/api/v1/obs/badevann/get?incobs=false&time=latest&nearest=%7B%22maxdist%22%3A${maxDistance}%2C%22maxcount%22%3A${maxResultCount}%2C%22points%22%3A%5B%7B%22lon%22%3A${longitude}%2C%22lat%22%3A${latitude}%7D%5D%7D")
            .body<FrostJsonResponse>()

        return response.data.tseries
    }
}