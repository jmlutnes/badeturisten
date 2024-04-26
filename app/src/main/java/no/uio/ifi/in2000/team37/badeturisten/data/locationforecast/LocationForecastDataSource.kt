package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection.LocationForecastHttpClient
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin.LocationForecastData

class LocationForecastDataSource(@LocationForecastHttpClient private val client: HttpClient) {
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

