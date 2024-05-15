package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import org.junit.Test
import org.junit.Assert.*

class SkrapUrlTest {

    private val datasource = OsloKommuneDatasource(HttpClient())

    @Test
    fun testWaterQualityShouldBeBad() = runTest {

        val waterQuality = datasource.scrapeUrl("https://www.oslo.kommune.no/natur-kultur-og-fritid/tur-og-friluftsliv/badeplasser-og-temperaturer/sorenga-sjobad/")?.waterQuality

        assertEquals(waterQuality, "Dårlig")
    }

    @Test
    fun testShouldReturnNull() = runTest{
        val response = datasource.scrapeUrl("wronglink")

        assertEquals(response, null)
    }
}