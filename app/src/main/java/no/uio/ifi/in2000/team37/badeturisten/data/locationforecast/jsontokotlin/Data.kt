package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("instant")
    val instant: Instant,
    @SerializedName("next_12_hours")
    val next12Hours: Next12Hours,
    @SerializedName("next_1_hours")
    val next1Hours: Next1Hours,
    @SerializedName("next_6_hours")
    val next6Hours: Next6Hours
)