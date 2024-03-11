package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinLocationForecast


import com.google.gson.annotations.SerializedName

data class Next12Hours(
    @SerializedName("details")
    val details: DetailsX,
    @SerializedName("summary")
    val summary: Summary
)