package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

class EnTurGeocoderRepositoryImp(
    private val datasource: EnTurGeocoderDataSource
): EnTurGeocoderRepository {
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

    override suspend fun hentBussruteName(navn: String): Bussstasjoner? {
        val stoppesteder = datasource.getDataName(navn)
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
