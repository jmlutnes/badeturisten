package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

class EnTurGeocoderRepository(val dataSource: EnTurGeocoderDataSource) {
    //Henter fra lokasjon og lager bussstasjon data class, deretter returnerer busstasjon
    /**
     * Send in latitude and longitude to fetch all the stop places in the nearby area.
     * To change the radius for search, change the radius in EnTurGeocoderDataSource
     */
    suspend fun hentBussruteLoc(lat: Double, lon: Double): Bussstasjoner? {
        val nearestStopPlace = dataSource.getDataLoc(lat, lon)
        val bussstasjoner = nearestStopPlace.features.map { feature ->
                Bussstasjon(
                    id = feature.properties.id,
                    navn = feature.properties.name,
                    koordinater = Pair(
                        feature.geometry.coordinates[0],
                        feature.geometry.coordinates[1]
                    )
                )
        }
        return if (bussstasjoner.isNotEmpty()) Bussstasjoner(bussstasjoner) else null
    }

    //Basert paa navn og ikke lokasjon
    /**
     * Send in site name to fetch all the stop places in the nearby area.
     */
    suspend fun hentBussruteName(navn: String): Bussstasjoner? {
        val stoppesteder = dataSource.getDataName(navn)
        val bussstasjoner = stoppesteder.features.map { feature ->
                Bussstasjon(
                    id = feature.properties.id,
                    navn = feature.properties.name,
                    koordinater = Pair(
                        feature.geometry.coordinates[0],
                        feature.geometry.coordinates[1]
                    )
                )
        }
        return if (bussstasjoner.isNotEmpty()) Bussstasjoner(bussstasjoner) else null
    }
}
