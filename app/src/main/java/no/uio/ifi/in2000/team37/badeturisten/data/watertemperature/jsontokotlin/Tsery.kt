package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin

import com.google.gson.annotations.SerializedName

data class Tsery(
    @SerializedName("header")
    val header: Header,
    @SerializedName("observations")
    val observations: List<Observation>
)