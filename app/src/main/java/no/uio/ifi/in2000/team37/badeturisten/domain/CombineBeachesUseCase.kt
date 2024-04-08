package no.uio.ifi.in2000.team37.badeturisten.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.team37.badeturisten.data.beach.BeachRepository
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class CombineBeachesUseCase (
    private val beachRepository : BeachRepository,
    private val osloKommuneRepository : OsloKommuneRepository
){
    private val defaultDispatcher = Dispatchers.Default

    suspend operator fun invoke(): List<Beach> = withContext(defaultDispatcher) {
        val beachesFromMet = beachRepository.getBeachObservations().value
        val beachesFromOsloKommune = osloKommuneRepository.makeBeaches(0.0, 0.0)

        combineBeaches(beachesFromMet = beachesFromMet, beachesFromOsloKommune = beachesFromOsloKommune)
    }

    /*
    * This function removes duplicates by overwriting beache from MET with beaches from Oslo Kommune.
    * When two beaches have the same name, the ones from O-K are prioritized,
    * because they include both temperature and facilities.
    */

    fun combineBeaches(beachesFromMet: List<Beach>, beachesFromOsloKommune: List<Beach>): List<Beach> {
        val combinedMap = beachesFromMet.associateBy { it.name }.toMutableMap()

        beachesFromOsloKommune.forEach { beach ->
            combinedMap[beach.name] = beach
        }

        return combinedMap.values.toList()
    }

}