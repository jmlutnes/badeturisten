package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.Bussrute

class EnTurJourneyPlannerRepository (val dataSource: EnTurJourneyPlannerDataSource) {
    /**
     * Send in Buss station ID (Using the EnTurGeocoder) to receive all the busses related to the station.
     * Makes Bussrute objects with the line, name, and transport mode (bus/tram/coach/water)
     * returns a mutable list with all the busses related to the buss station.
     */
    suspend fun hentBussruterMedId(bussstasjonId: String): MutableList<Bussrute>? {
        val linjer = mutableListOf<Bussrute>() // Lokal instans av listen

        try {
            // Henter rutedata basert på busstasjonens ID
            val ruteData: jsontokotlinenturjourneyplanner = dataSource.getRute(bussstasjonId)
            ruteData.data.stopPlace.estimatedCalls.forEach { estimatedCall ->
                val line = estimatedCall.serviceJourney.journeyPattern.line
                linjer.add(Bussrute(line.publicCode, line.name, line.transportMode))
            }

            return linjer
        } catch (e: Exception) {
            println("En feil oppstod ved henting av bussruter: ${e.message}")
            return null
        }
    }
}