package no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts


import com.google.gson.annotations.SerializedName

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