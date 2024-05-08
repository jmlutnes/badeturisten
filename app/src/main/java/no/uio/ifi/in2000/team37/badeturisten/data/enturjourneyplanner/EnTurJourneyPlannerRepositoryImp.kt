package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.Bussrute
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import javax.inject.Inject

class EnTurJourneyPlannerRepositoryImp @Inject constructor(
    private val datasource: EnTurJourneyPlannerDataSource
) : EnTurJourneyPlannerRepository {
    /**
     * Send in Buss station ID (Using the EnTurGeocoder) to receive all the busses related to the station.
     * Makes Bussrute objects with the line, name, and transport mode (bus/tram/coach/water)
     * returns a mutable list with all the busses related to the buss station.
     */
    override suspend fun hentBussruterMedId(bussstasjonId: String): MutableList<Bussrute>? {
        val linjer = mutableListOf<Bussrute>() // Lokal instans av listen

        return try {
            // Henter rutedata basert på busstasjonens ID
            val ruteData: jsontokotlinenturjourneyplanner = datasource.getRute(bussstasjonId)
            ruteData.data.stopPlace.estimatedCalls.forEach { estimatedCall ->
                val line = estimatedCall.serviceJourney.journeyPattern.line
                linjer.add(Bussrute(line.publicCode, line.name, line.transportMode))
            }
            linjer
        } catch (e: Exception) {
            println("En feil oppstod ved henting av bussruter: ${e.message}")
            null
        }
    }
}