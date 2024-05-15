package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.BusStations

interface EnTurGeocoderRepository {

    /**
     * Send in latitude and longitude to fetch all the stop places in the nearby area.
     * To change the radius for search, change the radius in EnTurGeocoderDataSource
     */

    suspend fun fetchBusRouteLoc(lat: Double, lon: Double): BusStations?

    /**
     * Send in site name to fetch all the stop places in the nearby area.
     */

    suspend fun fetchBusRouteName(name: String): BusStations?
}