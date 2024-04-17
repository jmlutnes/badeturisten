package no.uio.ifi.in2000.team37.badeturisten

import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import org.junit.Test
import org.junit.Assert.*

class SkrapUrlTest {

    @Test
    fun testWaterQualityShouldBeBad() = runTest {

        val datasource = OsloKommuneDatasource()
        val waterQuality = datasource.skrapUrl("https://www.oslo.kommune.no/natur-kultur-og-fritid/tur-og-friluftsliv/badeplasser-og-temperaturer/sorenga-sjobad/").kvalitetInfo

        assertEquals(waterQuality, "Dårlig")
    }
}