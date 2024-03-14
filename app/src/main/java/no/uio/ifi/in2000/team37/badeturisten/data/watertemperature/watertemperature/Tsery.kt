package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature


import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature.Header

data class Tsery(
    @SerializedName("header")
    val header: Header,
    @SerializedName("observations")
    val observations: Any
)