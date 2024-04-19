package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder.jsontokotlinenturgeocoder
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

data class Bussstasjoner(val bussstasjon: List<Bussstasjon>)

class EnTurGeocoderDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("https://api.entur.io/geocoder/v1/")
            header("ET-Client-Name", "in2000study-application")
            }

        install(ContentNegotiation) {
            gson{}
        }
    }

    /**
     * Fetch the nearby buss stations based on input latitude and longitude.
     * The radius and the amount of results can be changes.
     */
    suspend fun getDataLoc(
        lat: Double,
        lon: Double
    ): jsontokotlinenturgeocoder {
        val radius = 0.5
        //Rediger for aa begrense antall responser
        val size = 5
        val data =
            client.get("reverse?point.lat=$lat&point.lon=$lon&boundary.circle.radius=$radius&size=$size&layers=venue")

        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }

    /**
    *Fetch buss stations based on input name
     */
    suspend fun getDataName(
        navn: String
    ): jsontokotlinenturgeocoder {

        val data =
            client.get("autocomplete?text=$navn&layers=venue")

        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }
}