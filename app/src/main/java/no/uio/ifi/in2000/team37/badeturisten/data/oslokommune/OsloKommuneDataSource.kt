package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.dependencyinjection.OsloKommuneHttpClient
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import org.jsoup.Jsoup


class OsloKommuneDatasource(@OsloKommuneHttpClient private val client: HttpClient) {
    /**
     * Send in URL. Using Jsoup to scrape the website on Oslo Commune.
     * Returns a OsloKommuneBeachInfo object.
     */
    suspend fun scrapeUrl(url: String): OsloKommuneBeachInfo? {
        try {
            val response: HttpResponse = client.get(url)
            return if (response.status.value in 200..299) {
                val responseBody = response.bodyAsText()
                return scrapeBeachInfoFromResponse(responseBody)
            } else {
                null
            }
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Send in responseBody as string, and returns OsloKommuneBeachInfo.
     * Using Jsoup to scrape the given responseBody.
     * Fetches Water quality, facilities, and imageURL.
     * First the water quality is selected in the 'div.io-bathingsite area'.
     * Then to extract only the visible information (not color description and other information)
     * Then Facilities is selected from 'div.io-facts'. Then separates the the different facilities with '•' s separator.
     * Then it is iterated through to also separate in '•' inside the text (for example the different opening times)
     * to have them also appear on different lines.
     * Lastly the image carousel is selected ('ods-image-carousel') and then fetches the first URL in the carousel.
     * If there is no image URL, a default image URL is used.
     * Returns OsloKommuneBeachInfo object
     */
    private fun scrapeBeachInfoFromResponse(responseBody: String): OsloKommuneBeachInfo {
        val document = Jsoup.parse(responseBody)

        //Get water quality
        val qualitySection = document.select("div.io-bathingsite").firstOrNull()
        //Show only the visible text(remove color coding)
        val firstQualityH3 = qualitySection?.select("div.ods-collapsible-content h3")?.firstOrNull()
        //Result of water quality
        val waterQuality = firstQualityH3?.ownText()?.trim() ?: "Ingen informasjon."

        //Facilities
        val facilitiesSection = document.select("div.io-facts").firstOrNull()
        val facilitiesBuilder = StringBuilder()
        //Some of the text is formatted with '•' in between facilities.
        //This codes iterate and separate areas in the text where that occurs:
        facilitiesSection?.let { section ->
            val facilityList = section.select("h2:contains(Fasiliteter) + div ul li")
            facilityList.forEach { li ->
                val contentWithBrReplaced = li.html().replace("<br>", "•")
                val elements =
                    Jsoup.parse(contentWithBrReplaced).text().split("•").map { it.trim() }
                elements.forEach { tekst ->
                    val formattedText = tekst.removePrefix("• ").let {
                        if (it.isNotBlank()) "• $it\n" else ""
                    }
                    facilitiesBuilder.append(formattedText)
                }
            }
        }
        val facilities = facilitiesBuilder.toString().trim().ifEmpty { null }

        //Image
        val imageData = document.select("ods-image-carousel").attr(":images")
        val srcStart = imageData.indexOf("\"src\":\"") + "\"src\":\"".length
        val srcEnd = imageData.indexOf("\"", srcStart)
        val imageUrl: String = if (srcStart > -1 && srcEnd > -1 && srcStart < srcEnd) {
            imageData.substring(srcStart, srcEnd).replace("\\/", "/")
        } else {
            "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"
        }
        return OsloKommuneBeachInfo(waterQuality, facilities, imageUrl)
    }


    /**
     * Send in the facilities (boolean) to be searched for on the Oslo Commune website.
     * Adds the different parameters to the URL for to the API GET-call. Returns all the results.
     */
    suspend fun getDataForFacility(
        lifeguard: Boolean,
        childFriendly: Boolean,
        grill: Boolean,
        kiosk: Boolean,
        accessible: Boolean,
        toilets: Boolean,
        divingTower: Boolean,
    ): jsontokotlin_kommune {
        val lifeguardUrl = if (lifeguard) "&f_facilities_lifeguard=true" else ""
        val childFriendlyUrl = if (childFriendly) "&f_facilities_child_friendly=true" else ""
        val grillUrl = if (grill) "&f_facilities_grill=true" else ""
        val kioskUrl = if (kiosk) "&f_facilities_kiosk=true" else ""
        val accessibleUrl = if (accessible) "&f_facilities_accessible=true" else ""
        val toiletsUrl = if (toilets) "&f_facilities_toilets=true" else ""
        val divingTowerUrl = if (divingTower) "&f_facilities_diving_tower=true" else ""
        val url =
            "/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=0"
        val urlString =
            url + lifeguardUrl + childFriendlyUrl + grillUrl + kioskUrl + accessibleUrl + toiletsUrl + divingTowerUrl
        val data = client.get(urlString)
        return data.body<jsontokotlin_kommune>()
    }

    /**
     * Fetch all the bathing sites in the Oslo Commune API.
     */
    suspend fun getData(
    ): jsontokotlin_kommune? {
        return try {
            val data =
                client.get("/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=30")
            data.body<jsontokotlin_kommune>()
        } catch (e: Exception) {
            null
        }
    }
}



