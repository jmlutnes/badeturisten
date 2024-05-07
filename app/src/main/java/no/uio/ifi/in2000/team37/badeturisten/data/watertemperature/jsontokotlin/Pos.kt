package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Pos(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String
)