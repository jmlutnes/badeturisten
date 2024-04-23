package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts.MetAlerts

class MetAlertsDataSource(private val client: HttpClient) {
    suspend fun getData(): MetAlerts { //lat og lon send med
        val data = client.get("weatherapi/metalerts/2.0/all.json")
//val data = client.get("/weatherapi/locationforecast/2.0/compact?lat=$lat&lon=$lon").bodyAsText()
//val response = Json.decodeFromString<Locationforecast_compact_jtc>(data)
        val response = data.body<MetAlerts>()
        return response
    }
}