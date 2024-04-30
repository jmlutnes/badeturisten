package no.uio.ifi.in2000.team37.badeturisten

import io.ktor.client.HttpClient
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepositoryImp
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.WaterTemperatureDataSource
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import org.junit.Test
import org.junit.Assert.*

class CombineBeachesTest {

    private val useCase = CombineBeachesUseCase(
        BeachRepositoryImp(WaterTemperatureDataSource(HttpClient())), OsloKommuneRepositoryImp(
        OsloKommuneDatasource(HttpClient()))
    )
    @Test
    fun testCombineBeachesShouldReturnBeachFromListB() {
        val beachListA = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), 5.5)
        )
        val beachlistB = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), null)
        )

        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        assertEquals(combinedBeaches, beachlistB)
    }

    @Test
    fun testCombineBeachesShouldReturnAllBeaches() {
        val beachListA = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), 5.5),
            Beach("Øya", Pos("1.1", "1.1"), 5.5),
            Beach("Stranda", Pos("1.1", "1.1"), 5.5),
        )
        val beachlistB = listOf(
            Beach("Stupet", Pos("1.1", "1.1"), null),
            Beach("Badeplassen", Pos("1.1", "1.1"), null)
        )

        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        assertEquals(combinedBeaches.toSet(), (beachListA+beachlistB).toSet())
    }

    @Test
    fun testShouldReturnNoBeaches() {
        // Arrange
        val beachListA = listOf<Beach>()
        val beachlistB = listOf<Beach>()

        // Act
        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        // Assert
        assertEquals(combinedBeaches, listOf<Beach>())
    }

}