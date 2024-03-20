package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin.LocationForecastData

class LocationForecastDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
        }
        install(ContentNegotiation) {
            gson{}
        }
    }

    suspend fun getForecastData(): LocationForecastData? {
        // Henter værdata med koordinater for Oslo sentrum
        val response =
        client.get("weatherapi/locationforecast/2.0/compact?lat=59.91276&lon=10.74608")

        return if (response.status.value in 200 .. 299) {
            response.body<LocationForecastData>()
        } else {
            null
        }
    }
}

