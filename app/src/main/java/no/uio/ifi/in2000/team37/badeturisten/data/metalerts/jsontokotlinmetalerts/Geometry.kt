package no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts


import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<List<List<Any>>>,
    @SerializedName("type")
    val type: String
)