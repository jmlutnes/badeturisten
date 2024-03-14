package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature


import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature.DataX

data class FrostJsonResponse(
    @SerializedName("data")
    val `data`: DataX
)