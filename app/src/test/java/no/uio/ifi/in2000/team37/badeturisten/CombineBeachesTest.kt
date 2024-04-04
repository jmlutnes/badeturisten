package no.uio.ifi.in2000.team37.badeturisten

import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.CombineBeachesUseCase
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import org.junit.Test
import org.junit.Assert.*

class CombineBeachesTest {

    @Test
    fun testCombineBeachesShouldReturnBeachFromListA() {
        // Arrange
        val beachListA = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), 5.5, false)
        )
        val beachlistB = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), null, false)
        )
        val useCase = CombineBeachesUseCase()

        // Act
        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        // Assert
        assertEquals(combinedBeaches, beachListA)
    }

    @Test
    fun testCombineBeachesShouldReturnAllBeaches() {
        // Arrange
        val beachListA = listOf(
            Beach("Sørenga", Pos("1.1", "1.1"), 5.5, false),
            Beach("Øya", Pos("1.1", "1.1"), 5.5, false),
            Beach("Stranda", Pos("1.1", "1.1"), 5.5, false),
        )
        val beachlistB = listOf(
            Beach("Stupet", Pos("1.1", "1.1"), null, false),
            Beach("Badeplassen", Pos("1.1", "1.1"), null, false)
        )
        val useCase = CombineBeachesUseCase()

        // Act
        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        // Assert
        assertEquals(combinedBeaches.toSet(), (beachListA+beachlistB).toSet())
    }

    @Test
    fun testShouldReturnNoBeaches() {
        // Arrange
        val beachListA = listOf<Beach>()
        val beachlistB = listOf<Beach>()
        val useCase = CombineBeachesUseCase()

        // Act
        val combinedBeaches = useCase.combineBeaches(beachListA, beachlistB)

        // Assert
        assertEquals(combinedBeaches, listOf<Beach>())
    }

}