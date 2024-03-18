package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Properties(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("timeseries")
    val timeseries: List<Timesery>
)