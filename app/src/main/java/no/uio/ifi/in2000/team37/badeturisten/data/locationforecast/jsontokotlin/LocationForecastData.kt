package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class LocationForecastData(
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("type")
    val type: String
)