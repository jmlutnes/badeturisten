package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.OsloKommuneDatasource
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

interface OsloKommuneRepository {
    val datasource: OsloKommuneDatasource
    // definer metodene som trengs
    suspend fun getClass(
        lat: Double?, lon: Double?
    ): jsontokotlin_kommune
    suspend fun getDataForFasilitet(
        badevakt: Boolean,
        barnevennlig: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        tilpasning: Boolean,
        toalett: Boolean,
        badebrygge: Boolean
    ): jsontokotlin_kommune
    suspend fun makeBeachesFasiliteter(
        badevakt: Boolean,
        barnevennlig: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        tilpasning: Boolean,
        toalett: Boolean,
        badebrygge: Boolean
    ): List<Beach>
    suspend fun getRight(
        lat: Double, lon: Double
    ): jsontokotlin_kommune
    fun extractUrl(inputString: String): String
    suspend fun skrapUrl(input: String): BadevannsInfo
    suspend fun getVannkvalitetLoc(
        lat: Double?, lon: Double?
    ): BadevannsInfo?
    suspend fun getBadeplasser(
        lat: Double?, lon: Double?
    ): List<Feature>
    fun extractBeachFromHTML(html: String): String?
    suspend fun finnNettside(navn: String): BadevannsInfo?
    suspend fun makeBeaches(
        lon: Double, lat: Double
    ): List<Beach>
    suspend fun getBeach(beachName: String): Beach?
    suspend fun finnAlleNettside(): MutableMap<String, BadeinfoForHomescreen>
}