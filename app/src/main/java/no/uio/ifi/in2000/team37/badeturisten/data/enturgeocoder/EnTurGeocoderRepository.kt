package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

class EnTurGeocoderRepository(val dataSource: EnTurGeocoderDataSource) {
    suspend fun getDataName(navn: String): String? {
        val nearestStopPlace = dataSource.getDataName(navn)
        val value = nearestStopPlace.features.firstOrNull()?.properties?.name
        return value
    }

    suspend fun getDataLoc(lat: Double, lon: Double): String? {
        val nearestStopPlace = dataSource.getDataLoc(lat, lon)
        val value = nearestStopPlace.features.firstOrNull()?.properties?.name
        return value
    }

    suspend fun hentBussruteLoc(lat: Double, lon: Double): Bussstasjoner? {
        val nearestStopPlace = dataSource.getDataLoc(lat, lon)
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
    suspend fun hentBussruteName(navn: String): Bussstasjoner? {
        val nearestStopPlace = dataSource.getDataName(navn)
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
