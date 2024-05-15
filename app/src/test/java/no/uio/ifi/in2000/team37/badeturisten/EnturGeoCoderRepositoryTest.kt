package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation
import org.junit.Test
import org.junit.Assert.*

class EnturGeoCoderRepositoryTest {

    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/geocoder/v1/")
            header("ET-Client-Name", "in2000study-application")
        }
        install(ContentNegotiation) { gson { } }
    }
    private val repo = EnTurGeocoderRepositoryImp(EnTurGeocoderDataSource(client))


    @Test
    fun getBusRouteLocShouldReturnRoute() = runTest {
        val lat = 59.54
        val lon = 10.44

        val result = repo.fetchBusRouteLoc(lat, lon)

        assertNotNull("$result", result)
    }

    @Test
    fun getBusRouteLocShouldReturnEmptyList() = runTest {
        val lat = -999999.9
        val lon =  999999.9

        val result = repo.fetchBusRouteLoc(lat, lon)

        if (result != null) {
            assertEquals(result.busStation, listOf<BusStation>())
        } else {
            throw AssertionError("Expected empty result, was null")
        }
    }

    @Test
    fun getBusRouteNameShouldReturnRoute() = runTest {
        val beachName = "Tjuvholmen"

        val result = repo.fetchBusRouteName(beachName)

        assertNotNull("Expected a route, was null", result)
    }

    @Test
    fun getBusRouteNameShouldReturnEmptyList() = runTest {
        val beachName = "anwvownvowisnv"

        val result = repo.fetchBusRouteName(beachName)

        if (result != null) {
            assertEquals(result.busStation, listOf<BusStation>())
        } else {
            throw AssertionError("Expected empty result, was null")
        }
    }
}