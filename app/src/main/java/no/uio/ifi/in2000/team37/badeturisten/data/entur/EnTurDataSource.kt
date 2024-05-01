package no.uio.ifi.in2000.team37.badeturisten.data.entur

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.dependencyinjection.EnTurHttpClient
import no.uio.ifi.in2000.team37.badeturisten.data.entur.jsontokotlinenturgeocoder.JsonToKotlinEnTurGeoCoder


class EnTurDataSource(@EnTurHttpClient private val client: HttpClient) {
    suspend fun getData(
        navn: String
    ): JsonToKotlinEnTurGeoCoder { //lat og lon send med

        val data =
            client.get("https://api.entur.io/geocoder/v1/autocomplete?text=$navn&lang=en")

        val response = data.body<JsonToKotlinEnTurGeoCoder>()
        return response
    }
}
