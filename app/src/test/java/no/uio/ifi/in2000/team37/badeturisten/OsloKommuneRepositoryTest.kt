package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import org.junit.Test
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import org.junit.Assert.*
class OsloKommuneRepositoryTest {

    private val client = HttpClient {
        defaultRequest {
            url("https://www.oslo.kommune.no")
        }
        install(ContentNegotiation) { gson {} }
        install(HttpTimeout) {
            this.requestTimeoutMillis = 5000
            this.connectTimeoutMillis = 5000
            this.socketTimeoutMillis = 5000
        }
    }
    private val repo = OsloKommuneRepositoryImp(OsloKommuneDatasource(client))

    @Test
    fun testFindWebsiteShouldReturnBeachInfo() = runTest {
        val beachName = "Sørenga"

        val result = repo.findWebPage(beachName)

        assertNotNull("Expected beach, was $result",result)
    }

    @Test
    fun testFindWebsiteShouldReturnNull() = runTest {
        val beachName = "FakeBeach"

        val result = repo.findWebPage(beachName)

        assertNull("Expected null, was $result", result)
    }

    @Test
    fun testGetBeachShouldReturnBeach() = runTest {
        val beachName = "Årvolldammen"

        val result = repo.getBeach(beachName)

        assertNotNull(result)
    }

    @Test
    fun testGetBeachShouldReturnNull() = runTest {
        val beachName = "ThisIsAFakeBeach"

        val result = repo.getBeach(beachName)

        assertNull(result)
    }

}