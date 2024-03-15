package no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts


import com.google.gson.annotations.SerializedName
import no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts.Geometry
import no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts.Properties
import no.uio.ifi.in2000.team37.badeturisten.model.JsonToKotlinMetAlerts.When

data class Feature(
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("type")
    val type: String,
    @SerializedName("when")
    val whenX: When
)