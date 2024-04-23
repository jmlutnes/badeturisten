package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

class EnTurGeocoderRepositoryImp(
    private val datasource: EnTurGeocoderDataSource
): EnTurGeocoderRepository {
    /**
     * Send in latitude and longitude to fetch all the stop places in the nearby area.
     * To change the radius for search, change the radius in EnTurGeocoderDataSource
     */
    override suspend fun hentBussruteLoc(lat: Double, lon: Double): Bussstasjoner? {
        val nearestStopPlace = datasource.getDataLoc(lat, lon)
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

    /**
     * Send in site name to fetch all the stop places in the nearby area.
     */
    override suspend fun hentBussruteName(navn: String): Bussstasjoner? {
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
