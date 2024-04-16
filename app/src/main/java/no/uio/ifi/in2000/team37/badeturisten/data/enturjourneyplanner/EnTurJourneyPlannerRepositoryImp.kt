package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.ui.viewmodel.Bussrute
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import javax.inject.Inject

class EnTurJourneyPlannerRepositoryImp @Inject constructor (
    override val dataSource: EnTurJourneyPlannerDataSource
): EnTurJourneyPlannerRepository{
    override suspend fun hentBussruterMedId(bussstasjonId: String): MutableList<Bussrute>? {
        val linjer = mutableListOf<Bussrute>() // Lokal instans av listen

        return try {
            // Henter rutedata basert på busstasjonens ID
            val ruteData: jsontokotlinenturjourneyplanner = dataSource.getRute(bussstasjonId)
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