package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin.LocationForecastData

class LocationForecastDataSource(private val client: HttpClient) {
    suspend fun getForecastData(): LocationForecastData? {
        // Henter værdata med koordinater for Oslo sentrum
        val response =
        client.get("weatherapi/locationforecast/2.0/compact?lat=59.91276&lon=10.74608")

        return if (response.status.value in 200 .. 299) {
            response.body<LocationForecastData>()
        } else {
            null
        }
        /*try {
            val response = client.get("weatherapi/locationforecast/2.0/compact?lat=$lat&lon=$lon")
            if (response.status == HttpStatusCode.OK) {
                return Result.success(response.body())
            } else {
                return Result.failure(RuntimeException("Failed to fetch data: ${response.status.description}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }*/
    }
/*
    suspend fun getForecastData(lat: Double, lon: Double): LocationForecastData? {
        try {
            val url = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/compact"
            val response: HttpResponse = client.get(url) {
                parameter("lat", lat)
                parameter("lon", lon)
            }

            if (response.status == HttpStatusCode.OK) {
                // Assuming JSON response
                return Json.decodeFromString(LocationForecastData.serializer(), response.readText())
            } else {
                // Handle HTTP errors here
                println("Failed to fetch data: ${response.status.description}")
            }
        } catch (e: Exception) {
            // Handle exceptions from network request
            println("Exception occurred: ${e.message}")
        }
        return null
    }
*/
}

