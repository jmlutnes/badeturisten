package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.EnTurJourneyPlannerDataSource
import org.junit.Test
import org.junit.Assert.*

class EnTurJourneyPlannerDataSourceTest {

    private val client = HttpClient {
            defaultRequest {
                url("https://api.entur.io/journey-planner/v3/graphql")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("ET-Client-Name", "in2000study-application")
            }
            install(ContentNegotiation) { gson { } }
            install(HttpTimeout) {
                this.requestTimeoutMillis = 5000
                this.connectTimeoutMillis = 5000
                this.socketTimeoutMillis = 5000
            }
        }

    private val dataSource = EnTurJourneyPlannerDataSource(client)
    @Test
    fun testGetRouteShouldReturnResult() = runTest {
        // Valid id
        val stopId = "59705"

        val result = dataSource.getRoute(stopId)

        assertNotNull("Expected a result, was null", result)
    }

    @Test
    fun testGetRouteShouldReturnNull() = runTest {
        // Valid id
        val stopId = "999999999"

        val result = dataSource.getRoute(stopId)

        if (result != null) {
            assertNull("Expected null, was a result", result.data.stopPlace)
        } else throw AssertionError("Expected empty result, but object was null")
    }
}