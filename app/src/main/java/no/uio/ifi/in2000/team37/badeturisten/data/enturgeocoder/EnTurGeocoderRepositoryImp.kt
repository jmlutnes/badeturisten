package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Busstation

class EnTurGeocoderRepositoryImp(
    private val datasource: EnTurGeocoderDataSource,
) : EnTurGeocoderRepository {
    override suspend fun fetchBusRouteLoc(lat: Double, lon: Double): Busstations? {
        val nearestStopPlace = datasource.getDataLoc(lat, lon)
        val busstations = nearestStopPlace.features.map { feature ->
            Busstation(
                id = feature.properties.id, name = feature.properties.name, coordinates = Pair(
                    feature.geometry.coordinates[0], feature.geometry.coordinates[1]
                )
            )
        }
        return if (busstations.isNotEmpty()) Busstations(busstations) else null
    }

    override suspend fun fetchBusRouteName(name: String): Busstations? {
        val stops = datasource.getDataName(name)
        val busstations = stops.features.map { feature ->
            Busstation(
                id = feature.properties.id, name = feature.properties.name, coordinates = Pair(
                    feature.geometry.coordinates[0], feature.geometry.coordinates[1]
                )
            )
        }
        return if (busstations.isNotEmpty()) Busstations(busstations) else null
    }
}
