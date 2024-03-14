package no.uio.ifi.in2000.team37.badeturisten.model.watertemperature


import com.google.gson.annotations.SerializedName

data class WaterTemperatureAPIResponse(
    @SerializedName("data")
    val `data`: Data
)