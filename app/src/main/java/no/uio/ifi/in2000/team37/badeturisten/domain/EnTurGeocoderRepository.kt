package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.Bussstasjoner
import no.uio.ifi.in2000.team37.badeturisten.data.enturgeocoder.EnTurGeocoderDataSource
import no.uio.ifi.in2000.team37.badeturisten.model.enTur.Bussstasjon

interface EnTurGeocoderRepository {
    val dataSource: EnTurGeocoderDataSource
    suspend fun hentBussruteLoc(lat: Double, lon: Double): Bussstasjoner?
    suspend fun hentBussruteName(navn: String): Bussstasjoner?
}