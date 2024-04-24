package no.uio.ifi.in2000.team37.badeturisten

import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
class OsloKommuneRepositoryTest {

    private val repo = OsloKommuneRepository()

    @Test
    fun testFindWebsiteShouldReturnBeach() = runTest {
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

}