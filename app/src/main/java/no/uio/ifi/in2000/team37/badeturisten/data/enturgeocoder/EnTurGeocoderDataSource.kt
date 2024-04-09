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
            url("")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        install(ContentNegotiation) {
            gson{}
        }
    }
    suspend fun getDataLoc(
        lat: Double,
        lon: Double
    ): jsontokotlinenturgeocoder {
        val radius = 0.5
        val size = 5
        val data =
            client.get("https://api.entur.io/geocoder/v1/reverse?point.lat=$lat&point.lon=$lon&boundary.circle.radius=$radius&size=$size&layers=venue")

        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }
    suspend fun getDataName(
        navn: String
    ): jsontokotlinenturgeocoder {

        val data =
            client.get("https://api.entur.io/geocoder/v1/autocomplete?text=$navn&layers=venue")

        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }
}