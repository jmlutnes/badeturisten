package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderRepositoryImp
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

        val result = repo.hentBussruteLoc(lat, lon)

        assertNotNull("$result", result)
    }

    @Test
    fun getBusRouteLocShouldReturnNull() = runTest {
        val lat = -999999.9
        val lon =  999999.9

        val result = repo.hentBussruteLoc(lat, lon)

        assertNull(result)
    }

    @Test
    fun getBusRouteNameShouldReturnRoute() = runTest {
        val beachName = "Ulvøya"

        val result = repo.hentBussruteName(beachName)

        assertNotNull("$result", result)
    }

    @Test
    fun getBusRouteNameShouldReturnNull() = runTest {
        val beachName = "anwvownvowisnv"

        val result = repo.hentBussruteName(beachName)

        assertNull(result)
    }
}