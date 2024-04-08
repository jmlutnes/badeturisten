package no.uio.ifi.in2000.team37.badeturisten.data.entur

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.team37.badeturisten.data.entur.jsontokotlinenturgeocoder.JsonToKotlinEnTurGeoCoder


class EnTurDataSource {
    private val client = HttpClient {
        defaultRequest {
            url("")
            header(HttpHeaders.ContentType, ContentType.Application.Json)        }
        install(ContentNegotiation) {
            gson{}
        }
    }

    suspend fun getData(
        navn: String
    ): JsonToKotlinEnTurGeoCoder {

        val data =
            client.get("https://api.entur.io/geocoder/v1/autocomplete?text=$navn&layers=venue\n")

        val response = data.body<JsonToKotlinEnTurGeoCoder>()
        return response
    }


}
