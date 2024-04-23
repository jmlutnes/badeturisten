package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import android.util.Log
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadeinfoForHomescreen
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach

class OsloKommuneRepository () {
    private val datasource: OsloKommuneDatasource = OsloKommuneDatasource()
    val liste: MutableList<Beach> = mutableListOf<Beach>()


    /**
     * Send in boolean parameters for which facilities Oslo Kommune website to seach for.
     * Returns all the beaches with at least one of the parameters.
     */
    suspend fun getDataForFasilitet(
        badevakt: Boolean,
        barnevennlig: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        tilpasning: Boolean,
        toalett: Boolean,
        badebrygge: Boolean
    ): jsontokotlin_kommune {
        return datasource.getDataForFasilitet(
            badevakt,
            barnevennlig,
            grill,
            kiosk,
            tilpasning,
            toalett,
            badebrygge
        )
    }

    /**
     *Send in boolean parameters for which facilities Oslo Commune website to search for.
     *For each of the beaches fetched from Oslo Commune extract the website for specific location,
     *and adds them to local list.
     *The name is the extracted from the HTML. Location is fetched from the beach.
     *Returns a list with all the beaches with the given facilities.
     */
    suspend fun makeBeachesFasiliteter(badevakt: Boolean, barnevennlig: Boolean, grill: Boolean, kiosk: Boolean, tilpasning: Boolean, toalett: Boolean, badebrygge: Boolean ): List<Beach>  {
        val lokalSokListe = mutableListOf<Beach>()
        val verdi = getDataForFasilitet(badevakt, barnevennlig, grill, kiosk, tilpasning, toalett, badebrygge)
        return try {
             val features = verdi.data.geoJson.features
            features.forEach { feature ->
                //Name
                val beachNameNotConverted: String? = feature.properties.popupContent
                val beachNameConverted: String? =
                    extractBeachFromHTML(beachNameNotConverted.toString())

                //Location
                val location = feature.geometry.coordinates
                val lon: String = location.get(0).toString()
                val lat: String = location.get(1).toString()
                val posisjon: Pos = Pos(lat, lon)

                lokalSokListe.add(Beach(beachNameConverted.toString(), posisjon, null, false))
            }
                return lokalSokListe
            }
            catch (e: Exception) {
                Log.d("Oslo Kommune repository", "failed to make beaches")
                Log.e("Oslo Kommune repos", e.message.toString())
                emptyList<Beach>()
        }
    }

    /**
     * Send in a HTML-string, and the URL in the string will be extracted using Regex
     * returns the URL as String
     */
    fun extractUrl(inputString: String): String {
        val streng = """href="(.*?)"""".toRegex()
        val matchResult = streng.find(inputString)
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
    fun extractBeachFromHTML(html: String): String {
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
    suspend fun finnAlleNettside(): MutableMap<String, BadeinfoForHomescreen> {
        val lokalSokListe = mutableMapOf<String, BadeinfoForHomescreen>()
        val features = getBadeplasser()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent.toString()
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            val url = extractUrl(beachNameNotConverted)
            val badeinfo = skrapUrl(url)
            lokalSokListe[beachNameConverted] = BadeinfoForHomescreen(beachNameConverted, badeinfo)
        }
        return lokalSokListe
    }

    /**
     * Send in name of a bathing site which is available on the Oslo Commune website.
     * Fetches all the sites on Oslo Commune website.
     * Goes through all the sites and converts the name and URL from Oslo Commune API.
     * Checks if the input site name exists in the Oslo Commune API.
     * The URL for that site is the scraped and returns OsloCommuneBeachInfo.
     */
    suspend fun finnNettside(navn: String): OsloKommuneBeachInfo? {
        val features = getBadeplasser()
        features.forEach { feature ->
            val beachNameNotConverted: String = feature.properties.popupContent
            val beachNameConverted: String = extractBeachFromHTML(beachNameNotConverted)
            if (beachNameConverted.contains(navn)) {
                val url = extractUrl(beachNameNotConverted)
                return skrapUrl(url)
            }
        }
            Log.e("OsloKommuneRepository", "No beach found")
            return null
    }

    /**
     * Create all the beaches existing in the Oslo Commune API.
     * Fetching beach name from HTML and position.
     * Returns a list with all the Beaches (bathing sites) on the Oslo Commune API.
     */
        suspend fun makeBeaches(): List<Beach> {
            return try {
                val features = getBadeplasser()
                println(features)
                features.forEach { feature ->
                    val beachNameNotConverted: String = feature.properties.popupContent
                    val beachNameConverted: String =
                        extractBeachFromHTML(beachNameNotConverted.toString())

                    val location = feature.geometry.coordinates
                    val lon: String = location.get(0).toString()
                    val lat: String = location.get(1).toString()

                    val posisjon: Pos = Pos(lat, lon)
                    liste.add(Beach(beachNameConverted, posisjon, null, false))
                }
                return liste

            } catch (e: Exception) {
                Log.d("Oslo Kommune repository", "failed to make beaches")
                Log.e("Oslo Kommune repos", e.message.toString())
                emptyList<Beach>()
            }
        }

    /**
     * Send in a bathing site name for Oslo Commune. Goes through all the sites and looks for the given name.
     * Returns the first beach with the given name.
     */
        suspend fun getBeach(beachName: String): Beach? {
            var beachlist: List<Beach> = makeBeaches()
            beachlist = beachlist.filter { beach -> beach.name == beachName }
            return beachlist.firstOrNull()
        }
    }



