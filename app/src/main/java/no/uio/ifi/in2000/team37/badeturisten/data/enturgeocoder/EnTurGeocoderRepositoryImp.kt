package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

class EnTurGeocoderRepositoryImp(
    private val datasource: EnTurGeocoderDataSource
) : EnTurGeocoderRepository {
    override suspend fun hentBussruteLoc(lat: Double, lon: Double): BusStations? {
        val nearestStopPlace = datasource.getDataLoc(lat, lon) ?: return null
        val busStations = nearestStopPlace.features.map { feature ->
            Bussstasjon(
                id = feature.properties.id,
                navn = feature.properties.name,
                koordinater = Pair(
                    feature.geometry.coordinates[0],
                    feature.geometry.coordinates[1]
                )
            )
        }

        return BusStations(busStations)
    }

    override suspend fun hentBussruteName(navn: String): BusStations? {
        val stops = datasource.getDataName(navn) ?: return null
        val busStations = stops.features.map { feature ->
            Bussstasjon(
                id = feature.properties.id,
                navn = feature.properties.name,
                koordinater = Pair(
                    feature.geometry.coordinates[0],
                    feature.geometry.coordinates[1]
                )
            )
        }

        return BusStations(busStations)
    }
}
