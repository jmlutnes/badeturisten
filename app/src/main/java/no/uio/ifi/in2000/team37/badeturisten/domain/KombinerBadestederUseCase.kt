package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class KombinerBadestederUseCase {
    private val beachRepository = BeachRepository()
    private val osloKommuneRepository = OsloKommuneRepository()
    private val defaultDispatcher = Dispatchers.Default

    suspend operator fun invoke(): List<Beach> = withContext(defaultDispatcher) {
        val beachesFromMet = beachRepository.getBeachObservations().value
        val beachesFromOsloKommune = osloKommuneRepository.makeBeaches(0.0, 0.0)

        val combinedMap = beachesFromOsloKommune.associateBy { it.name }.toMutableMap()

        beachesFromMet.forEach { beach ->
            combinedMap[beach.name] = beach
        }

        val combinedBeaches = combinedMap.values.toList()

        combinedBeaches
    }

}