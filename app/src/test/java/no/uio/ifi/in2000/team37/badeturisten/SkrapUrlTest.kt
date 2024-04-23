package no.uio.ifi.in2000.team37.badeturisten

import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import org.junit.Test
import org.junit.Assert.*

class SkrapUrlTest {

    val datasource = OsloKommuneDatasource()

    @Test
    fun testWaterQualityShouldBeBad() = runTest {

        val waterQuality = datasource.skrapUrl("https://www.oslo.kommune.no/natur-kultur-og-fritid/tur-og-friluftsliv/badeplasser-og-temperaturer/sorenga-sjobad/")?.waterQuality

        assertEquals(waterQuality, "Dårlig")
    }

    @Test
    fun testShouldReturnNull() = runTest{
        val response = datasource.skrapUrl("wronglink");

        assertEquals(response, null);
    }
}