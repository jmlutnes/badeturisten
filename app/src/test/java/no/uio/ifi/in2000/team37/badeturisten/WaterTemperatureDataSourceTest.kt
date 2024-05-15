package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Tsery
import org.junit.Test
import org.junit.Assert.*

class WaterTemperatureDataSourceTest {
    private val client = HttpClient {
        defaultRequest {
            url("https://havvarsel-frost.met.no")
        }
        install(ContentNegotiation) { gson {} }
        install(HttpTimeout) {
            this.requestTimeoutMillis = 5000
            this.connectTimeoutMillis = 5000
            this.socketTimeoutMillis = 5000
        }
    }

    private val dataSource = WaterTemperatureDataSource(client)

    @Test
    fun testGetDataShouldReturnResult() = runTest {
        val daysOfMeasurements: Long = 7

        val result = dataSource.getData(daysOfMeasurements)

        assertNotEquals(result, emptyList<Tsery>())
    }

    @Test
    fun testGetDataShouldReturnEmptyList() = runTest {
        val daysOfMeasurements: Long = -10

        val result = dataSource.getData(daysOfMeasurements)

        assertEquals(result, emptyList<Tsery>())
    }
}