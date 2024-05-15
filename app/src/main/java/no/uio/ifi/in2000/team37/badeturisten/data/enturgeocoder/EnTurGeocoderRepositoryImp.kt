package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurGeocoderRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation

class EnTurGeocoderRepositoryImp(
    private val datasource: EnTurGeocoderDataSource,
) : EnTurGeocoderRepository {

    override suspend fun fetchBusRouteLoc(lat: Double, lon: Double): BusStations? {
        val nearestStopPlace = datasource.getDataLoc(lat, lon) ?: return null
        val busStations = nearestStopPlace.features.map { feature ->
            BusStation(
                id = feature.properties.id, name = feature.properties.name, coordinates = Pair(
                    feature.geometry.coordinates[0], feature.geometry.coordinates[1]
                )
            )
        }
        return if (busStations.isNotEmpty()) BusStations(busStations) else BusStations(emptyList())
    }

    override suspend fun fetchBusRouteName(name: String): BusStations? {
        val stops = datasource.getDataName(name) ?: return null
        val busStations = stops.features.map { feature ->
            BusStation(
                id = feature.properties.id, name = feature.properties.name, coordinates = Pair(
                    feature.geometry.coordinates[0], feature.geometry.coordinates[1]
                )
            )
        }
        return if (busStations.isNotEmpty()) BusStations(busStations) else BusStations(emptyList())
    }
}
