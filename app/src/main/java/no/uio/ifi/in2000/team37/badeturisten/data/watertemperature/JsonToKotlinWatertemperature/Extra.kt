package no.uio.ifi.in2000.team37.badeturisten.data.watertemperature.JsonToKotlinWatertemperature


import com.google.gson.annotations.SerializedName

data class Extra(
    @SerializedName("name")
    val name: String,
    @SerializedName("pos")
    val pos: Pos
)