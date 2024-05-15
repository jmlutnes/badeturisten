package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import org.junit.Test
import org.junit.Assert.*

class EnTurGeoCoderDataSourceTest {
    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/geocoder/v1/")
            header("ET-Client-Name", "in2000study-application")
        }
        install(ContentNegotiation) { gson { } }
    }
    private val dataSource = EnTurGeocoderDataSource(client)

    @Test
    fun testGetDataNameShouldReturnResult() = runTest {
        val beachName = "Grinidammen"

        val result = dataSource.getDataName(beachName)

        if (result != null) {
            assertNotNull(result.bbox)
        } else {
            throw AssertionError("Expected a result, was null")
        }
    }

    @Test
    fun testGetDataNameShouldReturnEmptyResult() = runTest {
        val beachName = "Tullenavn"

        val result = dataSource.getDataName(beachName)

        if (result != null) {
            assertNull(result.bbox)
        }
    }
}