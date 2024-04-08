package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.Line
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.Bussrute

class EnTurJourneyPlannerRepository (val dataSource: EnTurJourneyPlannerDataSource) {
    suspend fun hentBussruterMedId(bussstasjonId: String): MutableList<Bussrute>? {
        val linjer = mutableListOf<Bussrute>() // Lokal instans av listen

        try {
            // Henter rutedata basert på busstasjonens ID
            val ruteData: jsontokotlinenturjourneyplanner = dataSource.getRute(bussstasjonId)
            ruteData.data.stopPlace.estimatedCalls.forEach { estimatedCall ->
                val line = estimatedCall.serviceJourney.journeyPattern.line
                linjer.add(Bussrute(line.publicCode, line.name))
            }

            return linjer
        } catch (e: Exception) {
            println("En feil oppstod ved henting av bussruter: ${e.message}")
            return null
        }
    }
}