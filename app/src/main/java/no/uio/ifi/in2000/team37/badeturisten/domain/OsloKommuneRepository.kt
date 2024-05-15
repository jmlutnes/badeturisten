package no.uio.ifi.in2000.team37.badeturisten.domain

import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Feature
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.model.beach.Beach
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BeachAndBeachInfo
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo

interface OsloKommuneRepository {

    /**
     * Send in boolean parameters for which facilities on the Oslo Commune website to look for.
     * Returns list of all the beaches with each of the given parameters.
     */
    suspend fun getDataForFacility(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ): List<jsontokotlin_kommune>

    /**
     *Send in boolean parameters for which facilities Oslo Commune website to search for.
     *For each of the beaches fetched from Oslo Commune extract the website for specific locations,
     *and adds them to local list.
     *The name is the extracted from the HTML. Location is fetched from the beach.
     *Returns a list with all the beaches with the given facilities.
     */
    suspend fun makeBeachesFacilities(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ): List<Beach>

    /**
     * Send in a HTML-string, and the URL in the string will be extracted using Regex
     * returns the URL as String
     */
    fun extractUrl(inputString: String): String

    /**
     * Send in URL, and Oslo Commune will be scraped for facilities, water quality and image URL.
     * Returns a OsloKommuneBeachInfo object
     */
    suspend fun scrapeUrl(input: String): OsloKommuneBeachInfo?

    /**
     * Get all the places on the Oslo Commune website.
     */
    suspend fun getBeaches(): List<Feature>

    /**
     * Send in HTML-String, and by using regex the name of the beach is returned
     */
    fun extractBeachFromHTML(html: String): String

    /**
     * Method to make a map with the name of the beach as key and the beach information as value.
     * Is used to get image on beachCard, and possibility to use information from Oslo Commune on different screens.
     * Fetches all the bathing sites from Oslo Commune website.
     * Get the name and URL for the site using extractBeachFromHTML method.
     * Then uses skrapUrl to fetch the OsloKommuneBeachInfo for the specific site.
     */
    suspend fun findAllWebPages(): MutableMap<String, BeachAndBeachInfo>

    /**
     * Send in name of a bathing site which is available on the Oslo Commune website.
     * Fetches all the sites on Oslo Commune website.
     * Goes through all the sites and converts the name and URL from Oslo Commune API.
     * Checks if the input site name exists in the Oslo Commune API.
     * The URL for that site is the scraped and returns OsloCommuneBeachInfo.
     */
    suspend fun findWebPage(name: String): OsloKommuneBeachInfo?

    /**
     * Create all the beaches existing in the Oslo Commune API.
     * Fetching beach name from HTML and position.
     * Returns a list with all the Beaches (bathing sites) on the Oslo Commune API.
     */
    suspend fun makeBeaches(): List<Beach>

    /**
     * Send in a bathing site name for Oslo Commune. Goes through all the sites and looks for the given name.
     * Returns the first beach with the given name.
     */
    suspend fun getBeach(beachName: String): Beach?
}