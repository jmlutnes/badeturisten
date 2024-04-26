package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import javax.inject.Inject

class OsloKommuneRepositoryImp @Inject constructor(
    private val datasource: OsloKommuneDatasource
): OsloKommuneRepository {
    private val beachList: MutableList<Beach> = mutableListOf()


    /**
     * Send in boolean parameters for which facilities Oslo Kommune website to seach for.
     * Returns all the beaches with at least one of the parameters.
     */
    override suspend fun getDataForFacility(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean
    ): jsontokotlin_kommune {
        return datasource.getDataForFasilitet(
            lifeguard,
            childFriendly,
            grill,
            kiosk,
            accessible,
            toilets,
            divingTower
        )
    }

    /**
     *Send in boolean parameters for which facilities Oslo Commune website to search for.
     *For each of the beaches fetched from Oslo Commune extract the website for specific location,
     *and adds them to local list.
     *The name is the extracted from the HTML. Location is fetched from the beach.
     *Returns a list with all the beaches with the given facilities.
     */
    suspend fun makeBeachesFacilities(lifeguard: Boolean, childFriendly: Boolean, grill: Boolean, kiosk: Boolean, accessible: Boolean, toilets: Boolean, divingTower: Boolean ): List<Beach>  {
        val localSearchList = mutableListOf<Beach>()
        val result = getDataForFacility(lifeguard, childFriendly, grill, kiosk, accessible, toilets, divingTower)
        return try {
             val features = result.data.geoJson.features
            features.forEach { feature ->
                //Name
                val beachNameNotConverted: String = feature.properties.popupContent
                val beachNameConverted: String =
                    extractBeachFromHTML(beachNameNotConverted)

                //Location
                val location = feature.geometry.coordinates
                val lon: String = location[0].toString()
                val lat: String = location[1].toString()
                val position = Pos(lat, lon)

                localSearchList.add(Beach(beachNameConverted.toString(), posisjon, null))
            }
            return localSearchList
        }
            catch (e: Exception) {
                Log.d("Oslo Kommune repository", "failed to make beaches")
                Log.e("Oslo Kommune repos", e.message.toString())
                emptyList()
        }
    }

    /**
     * Send in a HTML-string, and the URL in the string will be extracted using Regex
     * returns the URL as String
     */
    override fun extractUrl(inputString: String): String {
        val string = """href="(.*?)"""".toRegex()
        val matchResult = string.find(inputString)
        return matchResult?.groups?.get(1)?.value ?: ""
    }

    /**
     * Send in URL, and Oslo Commune will be scraped for facilities, waterquality and image URL.
     * Returns a OsloKommuneBeachInfo object
     */
    suspend fun skrapUrl(input: String): OsloKommuneBeachInfo? {
        return datasource.skrapUrl(input)
    }

    /**
     * Get all the places on the Oslo Commune website.
     */
    suspend fun getBadeplasser(): List<Feature> {
        val item = datasource.getData()
        return item.data.geoJson.features
    }

    /**
     * Send in HTML-String, and by using regex the name of the beach is returned
     */
   override fun extractBeachFromHTML(html: String): String {
        val regex = Regex("<a[^>]*>([^<]*)</a>")
        val matchResult = regex.find(html)
        if (matchResult != null) {
            return matchResult.groups[1]?.value ?: ""
        }
        return ""
    }

    /**
     * Method to make a map with the name of the beach as key and the bathinginformation as value.
     * Is used to get image on beachCard, and possibility to use information from Oslo Commune on different screens.
     * Fetches all the bathing sites from Oslo Commune website.
     * Get the name and URL for the site using extractBeachFromHTML method.
     * Then uses skrapUrl to fetch the OsloKommuneBeachInfo for the specific site.
     */
   override suspend fun findAllWebPages(): MutableMap<String, BeachInfoForHomescreen> {
        val localSearchList = mutableMapOf<String, BeachInfoForHomescreen>()
        val features = getBeaches()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            val url = extractUrl(beachNameNotConverted)
            val beachInfo = scrapeUrl(url)
            localSearchList[beachNameConverted] = BeachInfoForHomescreen(beachNameConverted, beachInfo)
        }
        return localSearchList
    }

    /**
     * Send in name of a bathing site which is available on the Oslo Commune website.
     * Fetches all the sites on Oslo Commune website.
     * Goes through all the sites and converts the name and URL from Oslo Commune API.
     * Checks if the input site name exists in the Oslo Commune API.
     * The URL for that site is the scraped and returns OsloCommuneBeachInfo.
     */
    suspend fun findWebPage(name: String): OsloKommuneBeachInfo? {
        val features = getBeaches()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            if (beachNameConverted.contains(name)) {
                val url = extractUrl(beachNameNotConverted)
                return scrapeUrl(url)
            }
        }
            // Log.e("OsloKommuneRepository", "No beach found")
            return null
    }

    /**
     * Create all the beaches existing in the Oslo Commune API.
     * Fetching beach name from HTML and position.
     * Returns a list with all the Beaches (bathing sites) on the Oslo Commune API.
     */
    override suspend fun makeBeaches(): List<Beach> {
            return try {
                val features = getBeaches()
                println(features)
                features.forEach { feature ->
                    val beachNameNotConverted: String = feature.properties.popupContent
                    val beachNameConverted: String =
                        extractBeachFromHTML(beachNameNotConverted)

                    val location = feature.geometry.coordinates
                    val lon: String = location[0].toString()
                    val lat: String = location[1].toString()

                    val position = Pos(lat, lon)
                    beachList.add(Beach(beachNameConverted, position, null, false))
                }
                return beachList

            } catch (e: Exception) {
                Log.d("Oslo Kommune repository", "failed to make beaches")
                Log.e("Oslo Kommune repos", e.message.toString())
                emptyList()
            }
        }

    /**
     * Send in a bathing site name for Oslo Commune. Goes through all the sites and looks for the given name.
     * Returns the first beach with the given name.
     */
      override  suspend fun getBeach(beachName: String): Beach? {
            var beachList: List<Beach> = makeBeaches()
            beachList = beachList.filter { beach -> beach.name == beachName }
            return beachList.firstOrNull()
        }
    }



