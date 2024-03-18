package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Instant(
    @SerializedName("details")
    val details: Details
)