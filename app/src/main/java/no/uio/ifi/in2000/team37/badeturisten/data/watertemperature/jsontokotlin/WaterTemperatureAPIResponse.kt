package no.uio.ifi.in2000.team37.badeturisten.model.watertemperature


import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature.Data

data class WaterTemperatureAPIResponse(
    @SerializedName("data")
    val `data`: Data
)