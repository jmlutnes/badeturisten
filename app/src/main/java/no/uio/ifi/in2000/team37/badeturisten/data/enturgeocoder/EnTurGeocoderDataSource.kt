package no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.jsontokotlinenturgeocoder.jsontokotlinenturgeocoder
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

data class Bussstasjoner(val bussstasjon: List<Bussstasjon>)

class EnTurGeocoderDataSource(private val client: HttpClient) {
    //Henter stasjoner basert paa longitude og latitude. Returnerer stasjoner i omraadet
    suspend fun getDataLoc(
        lat: Double,
        lon: Double
    ): jsontokotlinenturgeocoder {
        val radius = 0.5
        //Rediger for aa begrense antall responser
        val size = 5
        val data =
            client.get("https://api.entur.io/geocoder/v1/reverse?point.lat=$lat&point.lon=$lon&boundary.circle.radius=$radius&size=$size&layers=venue")
        //added geocoder/v1/ because client uses https://api.entur.io/
        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }
    //Henter stasjoner basert paa navn
    suspend fun getDataName(
        navn: String
    ): jsontokotlinenturgeocoder {

        val data =
            client.get("autocomplete?text=$navn&layers=venue")

        val response = data.body<jsontokotlinenturgeocoder>()
        return response
    }
}