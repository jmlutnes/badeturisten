package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune

import android.content.ClipData
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.util.appendIfNameAbsent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import java.lang.reflect.Type
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Algolia
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Item
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.Value
import no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune.jsontokotlin_kommune
import no.uio.ifi.in2000.team37.badeturisten.model.beach.BadevannsInfo
import org.jsoup.Jsoup


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


    suspend fun skrapUrl(url: String): BadevannsInfo {
        val response: HttpResponse = client.get(url)
        val responseBody = response.bodyAsText()
        val document = Jsoup.parse(responseBody)
        val badevannskvalitetSection = document.select("div.io-bathingsite").firstOrNull() ?: return BadevannsInfo("Informasjon om badevannskvalitet ble ikke funnet.", "", "")
        val title = document.title()
        // Henter generell informasjon om badevannskvalitet og temperatur
        val generellInfo = badevannskvalitetSection.select("div.ods-grid__column--12:not(:has(div))").text()

        // Henter spesifikk målt badevannskvalitet
        val måltKvalitetInfoBuilder = StringBuilder()
        badevannskvalitetSection.select("div.ods-grid__column--12 div.ods-text--align-center").forEach { column ->
            if (!column.text().contains("Ingen målt temperatur")) {
                måltKvalitetInfoBuilder.append(column.text()).append("\n")
            }
        }
        val måltKvalitetInfo = måltKvalitetInfoBuilder.toString().trim()

        return BadevannsInfo(generellInfo, måltKvalitetInfo, title)
    }

    suspend fun getDataFromLoc(
        longitude: Double?,
        latitude: Double?
    ): jsontokotlin_kommune { //lat og lon send med

        val data =
            client.get("https://www.oslo.kommune.no/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=30&address=%7B%22latitude%22:%22$latitude%22,%22longitude%22:%22$longitude%22,%22street_id%22:%22%22,%22street_name%22:%22%22,%22distance%22:2500%7D")

        val response = data.body<jsontokotlin_kommune>()
        return response
    }
    suspend fun getData(
        longitude: Double?,
        latitude: Double?
    ): jsontokotlin_kommune { //lat og lon send med

        val data =
            client.get("https://www.oslo.kommune.no/xmlhttprequest.php?category=340&rootCategory=340&template=78&service=filterList.render&offset=30")

        val response = data.body<jsontokotlin_kommune>()
        return response
    }

}


//Dette er metode som fikser problemet med at APIET har to forskjellige verdier med navn "value" hvor en er string og den andre er Value
val gson = Gson()

@Suppress("IMPLICIT_CAST_TO_ANY")
class ItemDeserializer : JsonDeserializer<Item> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Item {
        val jsonObject = json.asJsonObject

        // Ekstraher felles felt
        val id = jsonObject.get("id").asString
        val name = jsonObject.get("name").asString
        val label = jsonObject.get("label").asString
        val placeholder = jsonObject.get("placeholder").asString

        // Manuell deserialisering
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
