package no.uio.ifi.in2000.team37.badeturisten.data.oslokommune.jsontokotlinoslokommune
import com.google.gson.annotations.SerializedName

data class Center(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)