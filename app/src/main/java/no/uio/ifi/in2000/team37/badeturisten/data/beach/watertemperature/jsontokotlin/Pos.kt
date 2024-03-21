package no.uio.ifi.in2000.team37.badeturisten.data.beach.watertemperature.jsontokotlin

import com.google.gson.annotations.SerializedName

data class Pos(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lon")
    val lon: String
)