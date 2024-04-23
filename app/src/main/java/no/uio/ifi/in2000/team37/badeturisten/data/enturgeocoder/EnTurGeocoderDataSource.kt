package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.dependencyinjection.EnTurHttpClient
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder.jsontokotlinenturgeocoder
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

data class Bussstasjoner(val bussstasjon: List<Bussstasjon>)

class EnTurGeocoderDataSource(@EnTurHttpClient private val client: HttpClient) {
    /**
     * Fetch the nearby buss stations based on input latitude and longitude.
     * The radius and the amount of results can be changes.
     */
    suspend fun getDataLoc(
        lat: Double,
        lon: Double
    ): jsontokotlinenturgeocoder {
        //Change radius and size if necessary
        val radius = 0.5
        val size = 5
        val data =
            client.get("https://api.entur.io/geocoder/v1/reverse?point.lat=$lat&point.lon=$lon&boundary.circle.radius=$radius&size=$size&layers=venue")
        //added geocoder/v1/ because client uses https://api.entur.io/
        return data.body<jsontokotlinenturgeocoder>()
    }

    /**
     *Fetch buss stations based on input name
     */
    suspend fun getDataName(
        navn: String
    ): jsontokotlinenturgeocoder {
        val data =
            client.get("autocomplete?text=$navn&layers=venue")
        return data.body<jsontokotlinenturgeocoder>()
    }
}