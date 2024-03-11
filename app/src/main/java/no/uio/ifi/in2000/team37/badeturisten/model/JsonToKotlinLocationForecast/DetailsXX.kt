package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class DetailsXX(
    @SerializedName("precipitation_amount")
    val precipitationAmount: Double
)