package no.uio.ifi.in2000.team37.badeturisten.data.entur

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurRepository

class EnTurRepositoryImp(
    private val datasource: EnTurDataSource
): EnTurRepository {
    override suspend fun hentBussrute(navn: String): String? {
        val nearestStopPlace = datasource.getData(navn)
        val value = nearestStopPlace.features.firstOrNull()?.properties?.name
        println(nearestStopPlace)
        return value
    }
}
