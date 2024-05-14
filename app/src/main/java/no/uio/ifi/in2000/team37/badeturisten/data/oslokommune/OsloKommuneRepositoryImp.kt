package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.domain.OsloKommuneRepository
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachAndBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import javax.inject.Inject

class OsloKommuneRepositoryImp @Inject constructor(
    private val datasource: OsloKommuneDatasource,
) : OsloKommuneRepository {
    private val beachList: MutableList<Beach> = mutableListOf()

    override suspend fun getDataForFacility(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ): List<jsontokotlin_kommune> {
        val results = mutableListOf<jsontokotlin_kommune>()
        if (lifeguard) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = true,
                    childFriendly = false,
                    grill = false,
                    kiosk = false,
                    accessible = false,
                    toilets = false,
                    divingTower = false
                )
            )
        )
        if (childFriendly) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = true,
                    grill = false,
                    kiosk = false,
                    accessible = false,
                    toilets = false,
                    divingTower = false
                )
            )
        )
        if (grill) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = false,
                    grill = true,
                    kiosk = false,
                    accessible = false,
                    toilets = false,
                    divingTower = false
                )
            )
        )
        if (kiosk) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = false,
                    grill = false,
                    kiosk = true,
                    accessible = false,
                    toilets = false,
                    divingTower = false
                )
            )
        )
        if (accessible) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = false,
                    grill = false,
                    kiosk = false,
                    accessible = true,
                    toilets = false,
                    divingTower = false
                )
            )
        )
        if (toilets) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = false,
                    grill = false,
                    kiosk = false,
                    accessible = false,
                    toilets = true,
                    divingTower = false
                )
            )
        )
        if (divingTower) results.addAll(
            listOf(
                datasource.getDataForFacility(
                    lifeguard = false,
                    childFriendly = false,
                    grill = false,
                    kiosk = false,
                    accessible = false,
                    toilets = false,
                    divingTower = true
                )
            )
        )
        return results
    }

    override suspend fun makeBeachesFacilities(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ): List<Beach> {
        val localSearchList = mutableListOf<Beach>()

        val result = getDataForFacility(
            lifeguard, childFriendly, grill, kiosk, accessible, toilets, divingTower
        )

        return try {
            for (jsontokotlinKommune in result) {
                val features = jsontokotlinKommune.data.geoJson.features

                features.forEach { feature ->
                    val beachNameNotConverted: String = feature.properties.popupContent
                    val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)

                    val location = feature.geometry.coordinates
                    val lon: String = location[0].toString()
                    val lat: String = location[1].toString()
                    val position = Pos(lat, lon)

                    localSearchList.add(Beach(beachNameConverted, position, null))
                }
            }
            localSearchList
        } catch (e: Exception) {
            Log.d("Oslo Kommune repository", "Failed to make beaches: ${e.localizedMessage}")
            emptyList()
        }
    }

    override fun extractUrl(inputString: String): String {
        val string = """href="(.*?)"""".toRegex()
        val matchResult = string.find(inputString)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    override suspend fun scrapeUrl(input: String): OsloKommuneBeachInfo? {
        return datasource.scrapeUrl(input)
    }

    override suspend fun getBeaches(): List<Feature> {
        val item = datasource.getData()
        if (item != null) {
            return item.data.geoJson.features
        }
        return emptyList()
    }

    override fun extractBeachFromHTML(html: String): String {
        val regex = Regex("<a[^>]*>([^<]*)</a>")
        val matchResult = regex.find(html)
        if (matchResult != null) {
            return matchResult.groups[1]?.value ?: ""
        }
        return ""
    }

    override suspend fun findAllWebPages(): MutableMap<String, BeachAndBeachInfo> {
        val localSearchList = mutableMapOf<String, BeachAndBeachInfo>()
        val features = getBeaches()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            val url = extractUrl(beachNameNotConverted)
            val beachInfo = scrapeUrl(url)
            localSearchList[beachNameConverted] = BeachAndBeachInfo(beachNameConverted, beachInfo)
        }
        return localSearchList
    }

    override suspend fun findWebPage(name: String): OsloKommuneBeachInfo? {
        val features = getBeaches()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            if (beachNameConverted.contains(name)) {
                val url = extractUrl(beachNameNotConverted)
                return scrapeUrl(url)
            }
        }
        return null
    }

    override suspend fun makeBeaches(): List<Beach> {
        return try {
            val features = getBeaches()
            println(features)
            features.forEach { feature ->
                val beachNameNotConverted: String = feature.properties.popupContent
                val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)

                val location = feature.geometry.coordinates
                val lon: String = location[0].toString()
                val lat: String = location[1].toString()

                val position = Pos(lat, lon)
                if (!beachNameConverted.contains("Badstu") && !beachNameConverted.contains("Tømmerholtjern") && !beachNameConverted.contains(
                        "Smalvannet"
                    ) && !beachNameConverted.contains(
                        "Solbergvannet"
                    )
                ) {
                    beachList.add(Beach(beachNameConverted, position, null))
                }
            }
            return beachList

        } catch (e: Exception) {
            Log.d("Oslo Kommune repository", "failed to make beaches")
            Log.e("Oslo Kommune repos", e.message.toString())
            emptyList()
        }
    }

    override suspend fun getBeach(beachName: String): Beach? {
        var beachList: List<Beach> = makeBeaches()
        beachList = beachList.filter { beach -> beach.name == beachName }
        return beachList.firstOrNull()
    }
}