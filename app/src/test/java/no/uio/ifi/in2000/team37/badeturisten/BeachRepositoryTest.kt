package no.uio.ifi.in2000.team37.badeturisten

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import okhttp3.internal.wait
import org.junit.Test
import org.junit.Assert.*
class BeachRepositoryTest {

    private val beachRepository = BeachRepository()

    init {
        runTest {
            beachRepository.loadBeaches()
        }
    }

    @Test
    fun testShouldReturnEmptyListWhenResultFromDataSourceIsEmpty() {

        val beachList = beachRepository.makeBeaches(listOf())
        assertEquals(beachList, listOf<Beach>())
    }

    @Test
    fun testGetBeachShouldReturnBeach() = runTest {

        val beachName = "Tjuvholmen"
        val beach = beachRepository.getBeach(beachName)

        if (beach != null) {
            assertEquals(beach.name, beachName)
        } else throw AssertionError("Expected object of type Beach, was null")
    }

    @Test
    fun testGetBeachShouldReturnNull() = runTest {
        val beachRepository = BeachRepository()
        val beachName = "Tullenavn"

        val beach = beachRepository.getBeach(beachName)

        assertEquals(beach, null)
    }
}