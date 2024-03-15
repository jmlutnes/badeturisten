package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts


import com.google.gson.annotations.SerializedName

data class MetAlerts(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("lastChange")
    val lastChange: String,
    @SerializedName("type")
    val type: String
)