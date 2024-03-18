package no.uio.ifi.in2000.team37.badeturisten.data.locationforecast.jsontokotlin


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("units")
    val units: Units,
    @SerializedName("updated_at")
    val updatedAt: String
)