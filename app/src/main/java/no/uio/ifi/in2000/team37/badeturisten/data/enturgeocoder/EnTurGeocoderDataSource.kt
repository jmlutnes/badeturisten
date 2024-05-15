package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder.jsontokotlinenturgeocoder
import no.uio.ifi.in2000.team37.badeturisten.dependencyinjection.EnTurHttpGeocoderHttpClient
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.BusStation


data class BusStations(val busStation: List<BusStation>)

class EnTurGeocoderDataSource(@EnTurHttpGeocoderHttpClient private val client: HttpClient) {
    /**
     * Fetch the nearby buss stations based on input latitude and longitude.
     * The radius and the amount of results can be changes.
     */
    suspend fun getDataLoc(
        lat: Double,
        lon: Double
    ): jsontokotlinenturgeocoder? {
        //Change radius and size if necessary
        val radius = 1
        val size = 8
        return try {
            val data =
                client.get("reverse?point.lat=$lat&point.lon=$lon&boundary.circle.radius=$radius&size=$size&layers=venue")
            data.body<jsontokotlinenturgeocoder>()
        } catch (e: Exception) {
            null
        }
    }

    /**
     *Fetch buss stations based on input name
     */
    suspend fun getDataName(
        name: String
    ): jsontokotlinenturgeocoder? {
        return try {
            val data =
                client.get("autocomplete?text=$name&size=1&categories=onstreetBus,onstreetTram,airport,railStation,metroStation,busStation,coachStation,tramStation,harbourPort,ferryPort,ferryStop,liftStation,vehicleRailInterchange,other&boundary.county_ids=KVE:TopographicPlace:03,KVE:TopographicPlace:32")
            data.body<jsontokotlinenturgeocoder>()
        } catch (e: Exception) {
            e.message?.let { Log.e("geocoder", it) }
            null
        }
    }
}