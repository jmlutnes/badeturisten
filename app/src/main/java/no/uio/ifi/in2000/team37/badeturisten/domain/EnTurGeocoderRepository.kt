package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Bussstasjoner
interface EnTurGeocoderRepository {
    suspend fun hentBussruteLoc(lat: Double, lon: Double): Bussstasjoner?
    suspend fun hentBussruteName(navn: String): Bussstasjoner?
}