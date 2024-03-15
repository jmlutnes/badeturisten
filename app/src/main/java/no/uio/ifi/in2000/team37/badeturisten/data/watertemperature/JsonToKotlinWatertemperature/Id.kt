package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature


import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("buoyid")
    val buoyid: String,
    @SerializedName("parameter")
    val parameter: String,
    @SerializedName("source")
    val source: String
)