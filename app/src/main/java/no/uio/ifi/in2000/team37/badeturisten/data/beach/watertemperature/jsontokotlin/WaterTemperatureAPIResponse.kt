package no.uio.ifi.in2000.team37.badeturisten.data.beach.watertemperature.jsontokotlin


import com.google.gson.annotations.SerializedName

data class WaterTemperatureAPIResponse(
    @SerializedName("data")
    val `data`: Data
)