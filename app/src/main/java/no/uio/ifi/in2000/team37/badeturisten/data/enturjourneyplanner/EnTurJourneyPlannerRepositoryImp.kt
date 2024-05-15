package no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.enturjourneyplanner.jsontokotlinenturjourneyplanner.jsontokotlinenturjourneyplanner
import no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile.BusRoute
import no.uio.ifi.in2000.team37.badeturisten.domain.EnTurJourneyPlannerRepository
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation
import javax.inject.Inject

class EnTurJourneyPlannerRepositoryImp @Inject constructor(
    private val datasource: EnTurJourneyPlannerDataSource,
) : EnTurJourneyPlannerRepository {

    override suspend fun fetchBusroutesById(
        busStationId: String,
        busStation: BusStation,
    ): MutableList<BusRoute>? {
        val lines = mutableListOf<BusRoute>() // Local instance of list

        return try {
            // fetch planning data based on buss station ID

            val routeData: jsontokotlinenturjourneyplanner? = datasource.getRoute(busStationId)
            if (routeData != null) {
                if (routeData.data.stopPlace.estimatedCalls.isEmpty() && routeData.data.stopPlace.transportMode[0] != "bus") {
                    val line = routeData.data.stopPlace
                    lines.add(
                        BusRoute(
                            null, line.name, line.transportMode[0], busStation
                        )
                    )
                } else {
                    routeData.data.stopPlace.estimatedCalls.forEach { estimatedCall ->
                        val line = estimatedCall.serviceJourney.journeyPattern.line
                        lines.add(
                            BusRoute(
                                line.publicCode, line.name, line.transportMode, busStation
                            )
                        )
                    }
                }
            }
            lines
        } catch (e: Exception) {
            e.message?.let { Log.e("journeyplanner", it) }
            null
        }
    }
}