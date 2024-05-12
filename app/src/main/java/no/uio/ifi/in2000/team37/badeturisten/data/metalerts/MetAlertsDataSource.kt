package no.uio.ifi.in2000.team37.badeturisten.data.metalerts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts.MetAlerts

class MetAlertsDataSource(private val client: HttpClient) {
    suspend fun getData(): MetAlerts { //lat og lon send med
        val data = client.get("weatherapi/metalerts/2.0/all.json")
        return data.body<MetAlerts>()
    }
}