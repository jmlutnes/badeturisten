package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature


import com.google.gson.annotations.SerializedName
<<<<<<<< HEAD:app/src/main/java/no/uio/ifi/in2000/team37/badeturisten/data/watertemperature/watertemperature/Tsery.kt
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.watertemperature.Header
========
>>>>>>>> master:app/src/main/java/no/uio/ifi/in2000/team37/badeturisten/data/watertemperature/jsontokotlin/Tsery.kt

data class Tsery(
    @SerializedName("header")
    val header: Header,
    @SerializedName("observations")
    val observations: List<Observation>
)