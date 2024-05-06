package no.uio.ifi.in2000.team37.badeturisten.model.beach

import androidx.datastore.core.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin.Pos
import org.jsoup.SerializationException
import java.io.InputStream
import java.io.OutputStream

object BeachListSerializer : Serializer<List<Beach>> {
    override val defaultValue: List<Beach>
        get() = emptyList()  // Default value is an empty list

    override suspend fun readFrom(input: InputStream): List<Beach> {
        return try{
            Json.decodeFromString(
                deserializer = ListSerializer(Beach.serializer()),  // Use ListSerializer to handle list of Beach
                string = input.readBytes().decodeToString()
            )
        }catch(e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<Beach>, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ListSerializer(Beach.serializer()),  // Serialize the list of Beach
                value = t
            ).encodeToByteArray()
        )
    }
}