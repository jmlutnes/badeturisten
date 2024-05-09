package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.Bussrute
import javax.inject.Inject

class EnTurJourneyPlannerRepositoryImp @Inject constructor(
    private val datasource: EnTurJourneyPlannerDataSource,
) : EnTurJourneyPlannerRepository {
    override suspend fun hentBussruterMedId(
        bussstasjonId: String,
        bussstasjon: Bussstasjon,
    ): MutableList<Bussrute>? {
        val lines = mutableListOf<Bussrute>() // Local instance of list

        return try {
            // fetch planning data based on buss station ID
            val ruteData: jsontokotlinenturjourneyplanner = datasource.getRute(bussstasjonId)
            ruteData.data.stopPlace.estimatedCalls.forEach { estimatedCall ->
                val line = estimatedCall.serviceJourney.journeyPattern.line
                lines.add(Bussrute(line.publicCode, line.name, line.transportMode, bussstasjon))
            }
            lines
        } catch (e: Exception) {
            println("En feil oppstod ved henting av bussruter: ${e.message}")
            null
        }
    }
}