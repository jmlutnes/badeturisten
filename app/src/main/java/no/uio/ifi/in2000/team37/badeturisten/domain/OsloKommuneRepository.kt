package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo

interface OsloKommuneRepository {
    //val datasource: OsloKommuneDatasource
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
    suspend fun skrapUrl(input: String): OsloKommuneBeachInfo
    suspend fun getVannkvalitetLoc(
        lat: Double?, lon: Double?
    ): OsloKommuneBeachInfo?
    suspend fun getBadeplasser(
        lat: Double?, lon: Double?
    ): List<Feature>
    fun extractBeachFromHTML(html: String): String?
    suspend fun finnNettside(navn: String): OsloKommuneBeachInfo?
    suspend fun makeBeaches(
        lon: Double, lat: Double
    ): List<Beach>
    suspend fun getBeach(beachName: String): Beach?
    suspend fun finnAlleNettside(): MutableMap<String, BadeinfoForHomescreen>
}