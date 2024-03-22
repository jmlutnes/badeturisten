package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.jsontokotlin



import com.google.gson.annotations.SerializedName

data class Observation(
    @SerializedName("body")
    val body: Body,
    @SerializedName("time")
    val time: String
)