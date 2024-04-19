package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import android.content.ClipData
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Algolia


import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Item
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Value
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.model.beach.OsloKommuneBeachInfo
import org.jsoup.Jsoup
import java.lang.reflect.Type


class OsloKommuneDatasource {
    val client = HttpClient() {
        defaultRequest {
            url("")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", "91eb6bae-3896-4da4-8a6a-a3a5266bf179")
        }
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(ClipData.Item::class.java, ItemDeserializer())
            }
        }
    }

    /**
     * Send in URL. Using Jsoup to scrape the website on Oslo Commune.
     * Returns a OsloKommuneBeachInfo object.
     */
    suspend fun skrapUrl(url: String): OsloKommuneBeachInfo {
        val response: HttpResponse = client.get(url)
        val responseBody = response.bodyAsText()
        return scrapeBeachInfoFromResponse(responseBody)
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
    fun scrapeBeachInfoFromResponse(responseBody: String): OsloKommuneBeachInfo {
        val document = Jsoup.parse(responseBody)

        //Get waterquality
        val kvalitetsSeksjon = document.select("div.io-bathingsite").firstOrNull()
        //Show only the visible text(remove colorcoding)
        val forsteKvalitetsH3 = kvalitetsSeksjon?.select("div.ods-collapsible-content h3")?.firstOrNull()
        //Result of water quality
        val vannkvalitet = forsteKvalitetsH3?.ownText()?.trim() ?: "Ingen informasjon."

        //Facilities
        val fasiliteterSection = document.select("div.io-facts").firstOrNull()
        val fasiliteterBuilder = StringBuilder()
        //Some of the text is formatted with '•' inbetween facilities.
        //This codes iterate and separate areas in the text where that occurs:
        fasiliteterSection?.let { section ->
            val fasiliteterListe = section.select("h2:contains(Fasiliteter) + div ul li")
            fasiliteterListe.forEach { li ->
                val innholdMedBrErstattet = li.html().replace("<br>", "•")
                val elementer = Jsoup.parse(innholdMedBrErstattet).text().split("•").map { it.trim() }
                elementer.forEach { tekst ->
                    val formattedText = tekst.removePrefix("• ").let {
                        if (it.isNotBlank()) "• $it\n" else ""
                    }
                    fasiliteterBuilder.append(formattedText)
                }
            }
        }
        val fasiliteter = fasiliteterBuilder.toString().trim().ifEmpty { null }

        //Image
        val imageData = document.select("ods-image-carousel").attr(":images")
        val srcStart = imageData.indexOf("\"src\":\"") + "\"src\":\"".length
        val srcEnd = imageData.indexOf("\"", srcStart)
        val bildeUrl: String = if (srcStart > -1 && srcEnd > -1 && srcStart < srcEnd) {
            imageData.substring(srcStart, srcEnd).replace("\\/", "/")
        } else {
            "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"
        }
        return OsloKommuneBeachInfo(vannkvalitet, fasiliteter, bildeUrl)
    }


    /**
     * Send in the facilities (boolean) to be searched for on the Oslo Commune website.
     * Adds the different parameters to the URL for to the API GET-call. Returns all the results.
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
        val badevaktUrl = if (badevakt) "&f_facilities_lifeguard=true" else ""
        val barnevennligUrl = if (barnevennlig) "&f_facilities_child_friendly=true" else ""
        val grillUrl = if (grill) "&f_facilities_grill=true" else ""
        val kioskUrl = if (kiosk) "&f_facilities_kiosk=true" else ""
        val tilpasningUrl = if (tilpasning) "&f_facilities_kiosk=true" else ""
        val toalettUrl = if (toalett) "&f_facilities_toilets=true" else ""
        val badebryggeUrl = if (badebrygge) "&f_facilities_diving_tower=true" else ""
        val url =
            "https://www.oslo.kommune.no/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=0"
        val urlString = url +
                badevaktUrl + barnevennligUrl + grillUrl + kioskUrl + tilpasningUrl + toalettUrl + badebryggeUrl
        val data = client.get(urlString)
        return data.body<jsontokotlin_kommune>()
    }

    /**
     * Fetch all the bathing sites in the Oslo Commune API.
     */
    suspend fun getData(
    ): jsontokotlin_kommune {
        val data =
            client.get("https://www.oslo.kommune.no/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=30")
        return data.body<jsontokotlin_kommune>()
    }
}

val gson = Gson()
/**
 * Help class to seperate data class objects with the same names.
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
class ItemDeserializer : JsonDeserializer<Item> {

    /**
     * Manual desearialize because Value has two equal variables
     */
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Item {
        val jsonObject = json.asJsonObject
        // Extract common areas
        val id = jsonObject.get("id").asString
        val name = jsonObject.get("name").asString
        val label = jsonObject.get("label").asString
        val placeholder = jsonObject.get("placeholder").asString

        // Manual deserialization
        val algolia = if (jsonObject.has("algolia")) {
            gson.fromJson(jsonObject.get("algolia"), Algolia::class.java)
        } else {
            null
        }
        val valueElement = jsonObject.get("value")
        val value = if (valueElement.isJsonObject) {
            gson.fromJson(valueElement, Value::class.java)
        } else {
            valueElement.asString
        }
        return Item(id, name, label, placeholder, algolia, value)
    }
}
