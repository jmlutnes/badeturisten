package no.uio.ifi.in2000.team37.badeturisten.data.metalerts.jsontokotlinmetalerts


import com.google.gson.annotations.SerializedName

data class Resource(
    @SerializedName("description")
    val description: String,
    @SerializedName("mimeType")
    val mimeType: String,
    @SerializedName("uri")
    val uri: String
)