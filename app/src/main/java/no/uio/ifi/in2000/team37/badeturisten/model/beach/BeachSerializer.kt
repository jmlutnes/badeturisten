package no.uio.ifi.in2000.team37.badeturisten.model.beach

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import org.jsoup.SerializationException
import java.io.InputStream
import java.io.OutputStream

object BeachSerializer : Serializer<Beach> {
    override val defaultValue: Beach
        get() = TODO("Not yet implemented")

    override suspend fun readFrom(input: InputStream): Beach {
        return try{
            Json.decodeFromString(
                deserializer = Beach.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch(e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Beach, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Beach.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}