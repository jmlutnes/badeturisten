package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.LocationForecastDataSource
import org.junit.Test
import org.junit.Assert.*

class LocationForecastDataSourceTest {

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

    private val dataSource = LocationForecastDataSource(client)

    @Test
    fun testGetForecastDataShouldReturnData() = runTest {
        val lat = 59.91276
        val lon = 10.74608

        val result = dataSource.getForecastData(lat, lon)

        assertNotNull(result)
    }

    @Test
    fun testGetForecastDataShouldReturnNull() = runTest {
        val lat = -9999.9
        val lon = -9999.9

        val result = dataSource.getForecastData(lat, lon)

        assertNull(result)
    }
}