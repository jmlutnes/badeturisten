package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<List<List<Any>>>,
    @SerializedName("type")
    val type: String
)