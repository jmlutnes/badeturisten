package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BusRoute

interface EnTurJourneyPlannerRepository {
    /**
     * Send in Buss station ID (Using the EnTurGeocoder) to receive all the busses related to the station.
     * Makes Bussrute objects with the line, name, and transport mode (bus/tram/coach/water)
     * returns a mutable list with all the busses related to the buss station.
     */
    suspend fun fetchBusroutesById(
        busStationId: String,
        busStation: BusStation,
    ): MutableList<BusRoute>?
}