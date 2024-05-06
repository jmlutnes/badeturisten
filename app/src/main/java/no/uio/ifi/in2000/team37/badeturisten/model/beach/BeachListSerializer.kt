package no.uio.ifi.in2000.team37.badeturisten.model.beach

import android.util.Log
import androidx.datastore.core.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.jsoup.SerializationException
import java.io.InputStream
import java.io.OutputStream

object BeachListSerializer : Serializer<List<Beach>> {
    override val defaultValue: List<Beach> = emptyList()

    override suspend fun readFrom(input: InputStream): List<Beach> {
        return try {
            Json.decodeFromString(ListSerializer(Beach.serializer()), input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            Log.e("DataStore", "Error deserializing beaches", e)
            defaultValue
        }
    }

    override suspend fun writeTo(t: List<Beach>, output: OutputStream) {
        output.write(Json.encodeToString(ListSerializer(Beach.serializer()), t).encodeToByteArray())
    }
}