package no.uio.ifi.in2000.team37.badeturisten.data.entur

import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon


data class Bussstasjoner(val bussstasjon: List<Bussstasjon>)
class EnTurRepository(val dataSource: EnTurDataSource) {
    suspend fun getData(navn: String): String? {
        val nearestStopPlace = dataSource.getData(navn)
        val value = nearestStopPlace.features.firstOrNull()?.properties?.name
        println(nearestStopPlace)
        return value
    }


    suspend fun hentBussrute(navn: String): Bussstasjoner? {
        val nearestStopPlace = dataSource.getData(navn)
        val bussstasjoner = nearestStopPlace.features.mapNotNull { feature ->
            if (feature.geometry?.coordinates?.size == 2) {
                Bussstasjon(
                    id = feature.properties?.id,
                    navn = feature.properties?.name,
                    koordinater = Pair(
                        feature.geometry.coordinates[0],
                        feature.geometry.coordinates[1]
                    )
                )
            } else {
                null
            }
        }
        return if (bussstasjoner.isNotEmpty()) Bussstasjoner(bussstasjoner) else null
    }
}
