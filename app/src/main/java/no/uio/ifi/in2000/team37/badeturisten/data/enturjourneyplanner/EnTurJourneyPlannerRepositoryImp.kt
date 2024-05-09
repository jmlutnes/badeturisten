package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BusRoute
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import javax.inject.Inject

class EnTurJourneyPlannerRepositoryImp @Inject constructor(
    private val datasource: EnTurJourneyPlannerDataSource
) : EnTurJourneyPlannerRepository {
    override suspend fun getBusRoutesById(id: String): MutableList<BusRoute>? {
        val lines = mutableListOf<BusRoute>() // Local instance of list

        return try {
            // fetch planning data based on buss station ID
            val ruteData: jsontokotlinenturjourneyplanner? = datasource.getRute(id)
            ruteData?.data?.stopPlace?.estimatedCalls?.forEach { estimatedCall ->
                val line = estimatedCall.serviceJourney.journeyPattern.line
                lines.add(BusRoute(line.publicCode, line.name, line.transportMode))
            }
            lines
        } catch (e: Exception) {
            e.message?.let { Log.d("journeyplanner", it) }
            null
        }
    }
}