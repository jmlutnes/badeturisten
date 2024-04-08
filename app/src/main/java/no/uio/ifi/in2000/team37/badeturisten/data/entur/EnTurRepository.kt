package no.uio.ifi.in2000.team37.badeturisten.data.entur

class EnTurRepository(val dataSource: EnTurDataSource) {
    suspend fun hentBussrute(navn: String): String? {
        val nearestStopPlace = dataSource.getData(navn)
        val value = nearestStopPlace.features.firstOrNull()?.properties?.name
        println(nearestStopPlace)
        return value
    }
}
