package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class CombineBeachesUseCase {
    private val beachRepository = BeachRepository()
    private val osloKommuneRepository = OsloKommuneRepository()
    private val defaultDispatcher = Dispatchers.Default

    suspend operator fun invoke(): List<Beach> = withContext(defaultDispatcher) {
        val beachesFromMet = beachRepository.getBeachObservations().value
        val beachesFromOsloKommune = osloKommuneRepository.makeBeaches(0.0, 0.0)

        combineBeaches(beachesFromMet = beachesFromMet, beachesFromOsloKommune = beachesFromOsloKommune)
    }

    // Denne metoden fjerner duplikater, og ettersom badestedene fra MET legges inn sist,
    // vil disse alltid overskrive et badested med samme navn fra OsloKommune.
    // Det er ønskelig, fordi badestedene fra MET inkluderer badetemperatur
    fun combineBeaches(beachesFromMet: List<Beach>, beachesFromOsloKommune: List<Beach>): List<Beach> {
        val combinedMap = beachesFromOsloKommune.associateBy { it.name }.toMutableMap()

        beachesFromMet.forEach { beach ->
            combinedMap[beach.name] = beach
        }

        return combinedMap.values.toList()
    }

}