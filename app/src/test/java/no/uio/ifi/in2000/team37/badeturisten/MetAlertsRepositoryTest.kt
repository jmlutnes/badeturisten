package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.metalerts.MetAlertsRepositoryImp
import org.junit.Test
import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MetAlertsRepositoryTest {
    private val client = HttpClient {
            defaultRequest {
                url("https://gw-uio.intark.uh-it.no/in2000/")
                header("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
            }
            install(ContentNegotiation) { gson {} }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }
   private val repo = MetAlertsRepositoryImp(MetAlertsDataSource(client))

    @Test
    fun testCalculateStatusShouldReturnInaktiv() {
        val endTime = "2024-04-20T09:00:00+00:00"
        val currentTime = LocalDateTime.parse("2024-05-20T09:00:00+00:00", DateTimeFormatter.ISO_DATE_TIME)

        val result = repo.calculateStatus(endTime, currentTime)

        assertEquals(result, "Inaktiv")
    }

    @Test
    fun testCalculateStatusShouldReturnAktiv() {
        val endTime = "2024-04-20T09:00:00+00:00"
        val currentTime = LocalDateTime.parse("2024-03-20T09:00:00+00:00", DateTimeFormatter.ISO_DATE_TIME)

        val result = repo.calculateStatus(endTime, currentTime)

        assertEquals(result, "Aktiv")
    }
}