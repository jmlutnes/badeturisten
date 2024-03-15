package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature


import com.google.gson.annotations.SerializedName

data class FrostJsonResponse(
    @SerializedName("data")
    val `data`: DataX
)